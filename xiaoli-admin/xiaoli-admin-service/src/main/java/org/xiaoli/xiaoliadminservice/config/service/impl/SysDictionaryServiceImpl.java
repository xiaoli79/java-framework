package org.xiaoli.xiaoliadminservice.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryTypeWriteReqDTO;
import org.xiaoli.xiaoliadminservice.config.domain.entity.SysDictionaryType;
import org.xiaoli.xiaoliadminservice.config.mapper.SysDictionaryTypeMapper;
import org.xiaoli.xiaoliadminservice.config.service.ISysDictionaryService;
import org.xiaoli.xiaolicommondomain.exception.ServiceException;


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
//      WHERE value = '状态' OR type_key = 'status'
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

}
