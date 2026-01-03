package org.xiaoli.xiaoliadminservice.map.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiaoli.xiaoliadminapi.map.constants.MapConstants;
import org.xiaoli.xiaoliadminservice.map.domain.dto.SysRegionDTO;
import org.xiaoli.xiaoliadminservice.map.domain.entity.SysRegion;
import org.xiaoli.xiaoliadminservice.map.mapper.RegionMapper;
import org.xiaoli.xiaoliadminservice.map.service.IXiaoliMapService;
import org.xiaoli.xiaolicommonredis.service.RedisService;

import java.util.ArrayList;
import java.util.List;


@Service
public class XiaoliMapServiceImpl implements IXiaoliMapService {

    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public List<SysRegionDTO> getCityList() {
//       声明一个空列表
        List<SysRegionDTO> result = new ArrayList<>();
//       查询数据库
        List<SysRegionDTO> cache = redisService.getCacheObject(MapConstants.CACHE_MAP_CITY_KEY, new TypeReference<List<SysRegionDTO>>() {
        });

        if(cache != null){
            return cache;
        }

        List<SysRegion> list = regionMapper.selectAllRegion();
//       提取城市数据列表，并且做对象转换
        for(SysRegion sysRegion : list){
            if(sysRegion.getLevel().equals(MapConstants.CITY_LEVEL)){
                SysRegionDTO sysRegionDTO = new SysRegionDTO();
                BeanUtils.copyProperties(sysRegion,sysRegionDTO);
                result.add(sysRegionDTO);
            }
        }
//      设置缓存
        redisService.setCacheObject(MapConstants.CACHE_MAP_CITY_KEY,result);
        return result;




    }
}
