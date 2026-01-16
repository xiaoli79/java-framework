package org.xiaoli.xiaoliadminservice.config.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryDataAddReqDTO;
import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryDataListReqDTO;
import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryTypeListReqDTO;
import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryTypeWriteReqDTO;
import org.xiaoli.xiaoliadminapi.config.domain.vo.DictionaryDataVo;
import org.xiaoli.xiaoliadminapi.config.domain.vo.DictionaryTypeVO;
import org.xiaoli.xiaoliadminservice.config.domain.entity.SysDictionaryData;
import org.xiaoli.xiaoliadminservice.config.domain.entity.SysDictionaryType;
import org.xiaoli.xiaoliadminservice.config.mapper.SysDictionaryDataMapper;
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

    /**
     * 字典类型数据的mapper
     */
    @Autowired
    private SysDictionaryDataMapper sysDictionaryDataMapper;

    /**
     * 字典类型的mapper
     */
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

    /**
     * 编辑字典类型
     * @param dictionaryTypeWriteReqDTO 编辑字典类型DTO
     * @return
     */
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

    /**
     * 新增字典数据
     * @param dictionaryDataAddReqDTO 字典类型的DTO
     * @return
     */
    @Override
    public Long addData(DictionaryDataAddReqDTO dictionaryDataAddReqDTO) {
        SysDictionaryType sysDictionaryType = sysDictionaryTypeMapper.selectOne(new LambdaQueryWrapper<SysDictionaryType>().eq(SysDictionaryType::getTypeKey, dictionaryDataAddReqDTO.getTypeKey()));

        if(sysDictionaryType == null){
            throw new ServiceException("这个字典类型不存在");
        }

//      判断字典数据是否存在,这个代码哪里有问题
        SysDictionaryData sysDictionaryData = (SysDictionaryData) sysDictionaryDataMapper.selectOne(
                new LambdaQueryWrapper<SysDictionaryData>()
                        .eq(SysDictionaryData::getDataKey, dictionaryDataAddReqDTO.getDataKey())
                        .or()
                        .eq(SysDictionaryData::getValue, dictionaryDataAddReqDTO.getValue())
        );

        if(sysDictionaryData != null){
            throw new ServiceException("字典数据键或值已存在");
        }

        sysDictionaryData = new SysDictionaryData();

        sysDictionaryData.setValue(dictionaryDataAddReqDTO.getValue());
        sysDictionaryData.setTypeKey(dictionaryDataAddReqDTO.getTypeKey());
        if(dictionaryDataAddReqDTO.getRemark() != null){
            sysDictionaryData.setRemark(dictionaryDataAddReqDTO.getRemark());
        }
        if(StringUtils.isNotBlank(dictionaryDataAddReqDTO.getValue())){
            sysDictionaryData.setValue(dictionaryDataAddReqDTO.getValue());
        }
        sysDictionaryDataMapper.insert(sysDictionaryData);
        return sysDictionaryData.getId();
    }

    @Override
    public BasePageVO<DictionaryDataVo> listData(DictionaryDataListReqDTO dictionaryDataListReqDTO) {

        BasePageVO<DictionaryDataVo> result = new BasePageVO<>();
//      需要模糊查询
        LambdaQueryWrapper<SysDictionaryData> queryWrapper = new LambdaQueryWrapper<>();
//      这是来判断typeKey是否一致!!!
        queryWrapper.eq(SysDictionaryData::getTypeKey, dictionaryDataListReqDTO.getTypeKey());
//      模糊匹配
        if(StringUtils.isNotBlank(dictionaryDataListReqDTO.getValue())){
            queryWrapper.likeRight(SysDictionaryData::getValue, dictionaryDataListReqDTO.getValue());
        }
//      进行分页查询
        Page<SysDictionaryData> page = sysDictionaryDataMapper.selectPage(new Page<>(dictionaryDataListReqDTO.getPageNo().longValue(), dictionaryDataListReqDTO.getPageSize().longValue()), queryWrapper);
        result.setTotals(Integer.parseInt(String.valueOf(page.getTotal())));
        result.setTotalPages(Integer.parseInt(String.valueOf(page.getPages())));

        List<DictionaryDataVo> list = new ArrayList<>();
        for(SysDictionaryData sysDictionaryData : page.getRecords()){
            DictionaryDataVo dictionaryDataVo = new DictionaryDataVo();
            BeanUtils.copyProperties(sysDictionaryData, dictionaryDataVo);
            list.add(dictionaryDataVo);
        }
        result.setList(list);
        return result;
    }
}
