package org.xiaoli.xiaoliadminservice.map.service;


import org.xiaoli.xiaoliadminservice.map.domain.dto.SysRegionDTO;

import java.util.List;
import java.util.Map;

public interface IXiaoliMapService {


    List<SysRegionDTO> getCityList();


    Map<String, List<SysRegionDTO>> getCityPinyinList();


    List<SysRegionDTO> getRegionChildren(Long parentId);

    List<SysRegionDTO> getHotCityList();
}



