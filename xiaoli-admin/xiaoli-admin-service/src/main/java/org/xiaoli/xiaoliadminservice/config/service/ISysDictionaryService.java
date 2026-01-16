package org.xiaoli.xiaoliadminservice.config.service;


import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryDataAddReqDTO;
import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryDataListReqDTO;
import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryTypeListReqDTO;
import org.xiaoli.xiaoliadminapi.config.domain.dto.DictionaryTypeWriteReqDTO;
import org.xiaoli.xiaoliadminapi.config.domain.vo.DictionaryDataVo;
import org.xiaoli.xiaoliadminapi.config.domain.vo.DictionaryTypeVO;
import org.xiaoli.xiaolicommondomain.domain.vo.BasePageVO;

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


    /**
     * 字典类型累表
     * @param dictionaryTypeListReqDTO 入参
     * @return BasePageVO
     */
    BasePageVO<DictionaryTypeVO> listType(DictionaryTypeListReqDTO dictionaryTypeListReqDTO);


    /**
     * 编辑字典类型
     * @param dictionaryTypeWriteReqDTO 编辑字典类型DTO
     * @return Long
     */
    Long editType(DictionaryTypeWriteReqDTO dictionaryTypeWriteReqDTO);


    /**
     * 新增字典类型
     * @param dictionaryDataAddReqDTO 字典类型的DTO
     * @return
     */
    Long addData(DictionaryDataAddReqDTO dictionaryDataAddReqDTO);

    BasePageVO<DictionaryDataVo> listData(DictionaryDataListReqDTO dictionaryDataListReqDTO);
}
