package org.xiaoli.xiaolicommonsecurity.domain.dto;


import lombok.Data;

/**
 * token信息
 */
@Data
public class TokenDTO {

    /**
     * 访问令牌
     */
    private String accessToken;


    /**
     * 过期时间
     */
    private Long expires;
}
