package org.xiaoli.xiaoliadminservice.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryTypeListReqDTO;
import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryTypeWriteReqDTO;
import org.xiaoli.xiaoliadminapi.config.domain.vo.DictionaryTypeVO;
import org.xiaoli.xiaoliadminservice.config.domain.entity.SysDictionaryType;
import org.xiaoli.xiaoliadminservice.config.mapper.SysDictionaryTypeMapper;
import org.xiaoli.xiaoliadminservice.config.service.ISysDictionaryService;
import org.xiaoli.xiaolicommondomain.domain.vo.BasePageVO;
import org.xiaoli.xiaolicommondomain.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;


/**
 * 字典服务的实现类
 */
//加上这个注解，把这个交给spring管理
@Service
public class SysDictionaryServiceImpl implements ISysDictionaryService {

    @Autowired
    private SysDictionaryTypeMapper sysDictionaryTypeMapper;

    @Override
    public Long addType(DictionaryTypeWriteReqDTO reqDTO) {
        LambdaQueryWrapper<SysDictionaryType> queryWrapper = new LambdaQueryWrapper<>();

//      SELECT id FROM sys_dictionary_type
//      WHERE value = 'value' OR type_key = 'typeKey'
//      这个下面的代码可以类似于这个~~
        queryWrapper.select(SysDictionaryType::getId).eq(SysDictionaryType::getValue, reqDTO.getValue()).or()
        .eq(SysDictionaryType::getTypeKey, reqDTO.getTypeKey());

        SysDictionaryType sysDictionaryType = sysDictionaryTypeMapper.selectOne(queryWrapper);
//      字典这个值已经存在，就不必在插入
        if (sysDictionaryType != null) {
            throw new ServiceException("字典类型键或者值已存在");
        }
//      进入数据准备工作
        sysDictionaryType = new SysDictionaryType();
        sysDictionaryType.setValue(reqDTO.getValue());
        sysDictionaryType.setTypeKey(reqDTO.getTypeKey());
        if(StringUtils.isNotBlank(reqDTO.getRemark())){
            sysDictionaryType.setRemark(reqDTO.getRemark());
        }
//      插入这些值~~
        sysDictionaryTypeMapper.insert(sysDictionaryType);
        return sysDictionaryType.getId();

    }

    /**
     * 查询字典类型
     * @param dictionaryTypeListReqDTO
     * @return
     */
    @Override
    public BasePageVO<DictionaryTypeVO> listType(DictionaryTypeListReqDTO dictionaryTypeListReqDTO) {

        BasePageVO<DictionaryTypeVO> result = new BasePageVO<>();

        LambdaQueryWrapper<SysDictionaryType> queryWrapper = new LambdaQueryWrapper<>();

//      模糊匹配
        if(StringUtils.isNotBlank(dictionaryTypeListReqDTO.getValue())){
            queryWrapper.likeRight(SysDictionaryType::getValue, dictionaryTypeListReqDTO.getValue());
        }

//      精准匹配
        if(StringUtils.isNotBlank(dictionaryTypeListReqDTO.getTypeKey())){
            queryWrapper.eq(SysDictionaryType::getTypeKey, dictionaryTypeListReqDTO.getTypeKey());
        }

//      进行分页查询
        Page<SysDictionaryType> page = sysDictionaryTypeMapper.selectPage(
                new Page<>(dictionaryTypeListReqDTO.getPageNo().longValue(),dictionaryTypeListReqDTO.getPageSize().longValue()),queryWrapper);

//      设置返回结果~~
        result.setTotals(Integer.parseInt(String.valueOf(page.getTotal())));
        result.setTotalPages(Integer.parseInt(String.valueOf(page.getPages())));
        List<DictionaryTypeVO> list = new ArrayList<>();
        for(SysDictionaryType sysDictionaryType : page.getRecords()){
            DictionaryTypeVO dictionaryTypeVO = new DictionaryTypeVO();
            BeanUtils.copyProperties(sysDictionaryType,dictionaryTypeVO);
            list.add(dictionaryTypeVO);
        }
        result.setList(list);
        return result;
    }

    @Override
    public Long editType(DictionaryTypeWriteReqDTO dictionaryTypeWriteReqDTO) {
        SysDictionaryType sysDictionaryType = sysDictionaryTypeMapper.selectOne(new LambdaQueryWrapper<SysDictionaryType>().eq(SysDictionaryType::getTypeKey, dictionaryTypeWriteReqDTO.getTypeKey()));

        if(sysDictionaryType == null){
            throw new ServiceException("字典类型不存在");
        }

        if(sysDictionaryTypeMapper.selectOne(new LambdaQueryWrapper<SysDictionaryType>()
                .ne(SysDictionaryType::getTypeKey,dictionaryTypeWriteReqDTO.getTypeKey())
        .eq(SysDictionaryType::getValue,dictionaryTypeWriteReqDTO.getValue())
        ) != null){
            throw new ServiceException("字典类型名称已存在");
        }

        sysDictionaryType.setValue(dictionaryTypeWriteReqDTO.getValue());
        sysDictionaryType.setRemark(dictionaryTypeWriteReqDTO.getRemark());
        sysDictionaryTypeMapper.updateById(sysDictionaryType);
        return sysDictionaryType.getId();

    }
}
