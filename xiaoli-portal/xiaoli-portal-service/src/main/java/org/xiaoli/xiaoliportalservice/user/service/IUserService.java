package org.xiaoli.xiaoliportalservice.user.service;


import org.xiaoli.xiaolicommonsecurity.domain.dto.TokenDTO;
import org.xiaoli.xiaoliportalservice.user.entity.dto.LoginDTO;

/**
 * 门户服务用户接口
 */
public interface IUserService {




    TokenDTO login(LoginDTO loginDTO);


    /**
     * 发送验证码
     * @param phone
     * @return
     */
    Boolean sendCode(String phone);
}
