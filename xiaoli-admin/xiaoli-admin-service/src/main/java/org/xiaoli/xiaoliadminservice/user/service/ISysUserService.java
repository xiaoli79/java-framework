package org.xiaoli.xiaoliadminservice.user.service;

import org.xiaoli.xiaoliadminservice.user.domain.dto.PasswordLoginDTO;
import org.xiaoli.xiaolicommonsecurity.domain.dto.TokenDTO;


/**
 * B端用户服务接口
 */
public interface ISysUserService {



    /**
     * B端用户登录
     * @param passwordLoginDTO
     * @return
     */
    TokenDTO login(PasswordLoginDTO passwordLoginDTO);


}
