package org.xiaoli.xiaoliadminservice.config.service;


import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryTypeWriteReqDTO;

/**
 * 字典服务的接口
 */

public interface ISysDictionaryService {


    /**
     * 新增字典类型
     * @param reqDTO 新增字典类型DTO
     * @return Long
     */
    Long addType(DictionaryTypeWriteReqDTO reqDTO);



}
