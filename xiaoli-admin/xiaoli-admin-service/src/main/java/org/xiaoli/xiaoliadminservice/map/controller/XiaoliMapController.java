package org.xiaoli.xiaoliadminservice.map.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.xiaoli.xiaoliadminapi.map.domain.vo.RegionVO;
import org.xiaoli.xiaoliadminapi.map.feign.MapFeignClient;
import org.xiaoli.xiaoliadminservice.map.domain.dto.SysRegionDTO;
import org.xiaoli.xiaoliadminservice.map.service.IXiaoliMapService;
import org.xiaoli.xiaolicommoncore.utils.BeanCopyUtil;
import org.xiaoli.xiaolicommondomain.domain.R;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class XiaoliMapController implements MapFeignClient {


    @Autowired
    private IXiaoliMapService xiaoliMapService;
    /**
     * 城市列表查询
     * @return 城市列表信息
     */

    @Override
    public R<List<RegionVO>> getCityList() {
        List<SysRegionDTO> cityList = xiaoliMapService.getCityList();
        List<RegionVO> result = BeanCopyUtil.copyList(cityList, RegionVO::new);
        return R.ok(result);
    }


    /**
     * 根据城市拼音归类查询
     * @return
     */
    @Override
    public R<Map<String, List<RegionVO>>> getCityPinyinList() {
        Map<String,List<RegionVO>> result = new LinkedHashMap<>();
        Map<String,List<SysRegionDTO>> pinyinList =  xiaoliMapService.getCityPinyinList();
        for(Map.Entry<String,List<SysRegionDTO>> entry:pinyinList.entrySet()){
            result.put(entry.getKey(),BeanCopyUtil.copyList(entry.getValue(),RegionVO::new));
        }
        return R.ok(result);
    }
}
