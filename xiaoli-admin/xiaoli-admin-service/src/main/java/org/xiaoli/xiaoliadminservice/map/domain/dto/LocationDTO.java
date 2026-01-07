package org.xiaoli.xiaoliadminservice.map.domain.dto;

import lombok.Data;

/**
 * 经纬度DTO
 */
@Data
public class LocationDTO {

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;


    /**
     * 格式化后的经纬度
     * @return
     */
    public String formatInfo(){
        return latitude + "," + longitude;
    }
}
