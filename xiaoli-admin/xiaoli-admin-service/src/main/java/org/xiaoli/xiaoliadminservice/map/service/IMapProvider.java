package org.xiaoli.xiaoliadminservice.map.service;


import org.xiaoli.xiaoliadminservice.map.domain.dto.PoiListDTO;
import org.xiaoli.xiaoliadminservice.map.domain.dto.SuggestSearchDTO;

/**
 * 地图服务提供者
 */
public interface IMapProvider {


    /**
     * 根据关键词搜索地点
     * @param suggestSearchDTO  搜索条件
     * @return 搜索结果
     */
    PoiListDTO searchQQMapPlaceByRegion(SuggestSearchDTO suggestSearchDTO);






}
