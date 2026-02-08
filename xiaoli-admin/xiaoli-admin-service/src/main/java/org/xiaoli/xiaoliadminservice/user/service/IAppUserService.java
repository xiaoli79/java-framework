package org.xiaoli.xiaoliadminservice.user.service;

import org.xiaoli.xiaoliadminapi.appUser.domain.dto.AppUserDTO;




public interface IAppUserService {



    /**
     * 根据微信注册用户
     * @param openId 用户微信ID
     * @return C端用户VO
     */
     AppUserDTO registerByOpenId (String openId);



    /**
     * 根据openid来查询用户信息
     * @param openId 用户微信ID
     * @return C端用户VO
     */
    AppUserDTO findByOpenId(String openId);



    /**
     * 根据手机号来查询用户信息
     * @param phoneNumber
     * @return
     */
    AppUserDTO findByPhone(String phoneNumber);

    /**
     * 根据手机号来注册用户信息
     * @param phoneNumber
     * @return
     */
    AppUserDTO registerByPhone(String phoneNumber);
}
