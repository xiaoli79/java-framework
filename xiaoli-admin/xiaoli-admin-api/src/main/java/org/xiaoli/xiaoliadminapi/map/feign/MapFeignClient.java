package org.xiaoli.xiaoliadminapi.map.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.xiaoli.xiaoliadminapi.map.domain.vo.RegionVO;
import org.xiaoli.xiaolicommondomain.domain.R;

import java.util.List;


/**
 * 区域信息VO
 */
@FeignClient(contextId = "mapFeignClient",value = "xiao-admin")
public interface MapFeignClient {

    /**
     * 城市列表查询
     * @return 城市列表信息
     */
    @GetMapping("/map/city_list")
    R<List<RegionVO>> getCityList();

}
