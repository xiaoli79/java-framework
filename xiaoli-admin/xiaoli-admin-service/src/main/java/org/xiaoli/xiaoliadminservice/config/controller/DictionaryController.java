package org.xiaoli.xiaoliadminservice.config.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryTypeWriteReqDTO;
import org.xiaoli.xiaoliadminservice.config.service.ISysDictionaryService;
import org.xiaoli.xiaolicommondomain.domain.R;


/**
 * 字典服务的相关的接口
 */
@RestController
public class DictionaryController {


    @Autowired
    private ISysDictionaryService sysDictionaryService;


    @PostMapping("/dictionary_type/add")
    public R<Long> addType(@RequestBody @Validated DictionaryTypeWriteReqDTO dictionaryTypeWriteReqDTO) {
        return  R.ok(sysDictionaryService.addType(dictionaryTypeWriteReqDTO));
    }



}
