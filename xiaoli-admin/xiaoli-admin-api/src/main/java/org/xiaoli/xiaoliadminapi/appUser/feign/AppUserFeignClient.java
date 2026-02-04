package org.xiaoli.xiaoliadminapi.appUser.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaoli.xiaoliadminapi.appUser.domain.vo.AppUserVO;
import org.xiaoli.xiaolicommondomain.domain.R;

/**
 * C端用户数据操作的远程调用
 */
@FeignClient(contextId = "appUserFeignClient",value="xiaoli-admin",path = "/app_user")
public interface AppUserFeignClient {


    /**
     * 根据微信注册用户
     * @param openId 用户微信ID
     * @return C端用户VO
     */
    @GetMapping("/register/openid")
    R<AppUserVO> registerByOpenId (@RequestParam String openId);


    /**
     * 根据openid来查询用户信息
     * @param openId 用户微信ID
     * @return C端用户VO
     */
    @GetMapping("/open_id_find")
    R<AppUserVO> fingByOpenId (@RequestParam String openId);


}
