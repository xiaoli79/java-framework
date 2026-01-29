package org.xiaoli.xiaoliadminservice.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xiaoli.xiaoliadminapi.appUser.domain.dto.AppUserDTO;
import org.xiaoli.xiaoliadminservice.user.domain.entity.AppUser;
import org.xiaoli.xiaoliadminservice.user.mapper.AppUserMapper;
import org.xiaoli.xiaoliadminservice.user.service.IAppUserService;
import org.xiaoli.xiaolicommondomain.domain.ResultCode;
import org.xiaoli.xiaolicommondomain.exception.ServiceException;


@Service
public class AppUserServiceImpl implements IAppUserService {


    @Autowired
    private AppUserMapper appUserMapper;

    @Value("${appuser.info.defaultAvatar}")
    private String defaultAvatar;
    /**
     * 根据微信注册用户
     * @param openId 用户微信ID
     * @return C端用户VO
     */
    @Override
    public AppUserDTO registerByOpenId(String openId) {

        /**
         * 判空逻辑
         */
        if(StringUtils.isEmpty(openId)){
            throw new ServiceException("微信ID不能为空", ResultCode.INVALID_PARA.getCode());
        }
//      进行设置默认值
        AppUser appUser = new AppUser();
        appUser.setOpenId(openId);
        appUser.setAvatar(defaultAvatar);
        appUser.setNickName("肥波的忠实粉"+(Math.random()*9000) + 1000);
//      执行插入逻辑
        appUserMapper.insert(appUser);
        AppUserDTO appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser,appUserDTO);
        appUserDTO.setOpenId(openId);

        return appUserDTO;
    }
}
