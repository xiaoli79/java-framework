package org.xiaoli.xiaoliadminservice.config.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryTypeWriteReqDTO;
import org.xiaoli.xiaoliadminservice.config.service.ISysDictionaryService;


@SpringBootTest
class SysDictionaryServiceImplTest {


    @Autowired
    private ISysDictionaryService sysDictionaryService;

    @Test
//  加上事务回滚~~
    @Transactional
    void addType() {

        DictionaryTypeWriteReqDTO dto = new DictionaryTypeWriteReqDTO();
        dto.setTypeKey("weight");
        dto.setValue("重量");
        dto.setRemark("重量配置");
        Assertions.assertTrue(sysDictionaryService.addType(dto) > 0L);
        ;
    }
}