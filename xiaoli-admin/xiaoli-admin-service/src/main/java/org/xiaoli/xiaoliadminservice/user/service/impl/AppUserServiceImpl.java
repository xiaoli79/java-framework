package org.xiaoli.xiaoliadminservice.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xiaoli.xiaoliadminapi.appUser.domain.dto.AppUserDTO;
import org.xiaoli.xiaoliadminapi.appUser.domain.dto.UserEditReqDTO;
import org.xiaoli.xiaoliadminservice.user.config.RabbitConfig;
import org.xiaoli.xiaoliadminservice.user.domain.entity.AppUser;
import org.xiaoli.xiaoliadminservice.user.domain.entity.SysUser;
import org.xiaoli.xiaoliadminservice.user.mapper.AppUserMapper;
import org.xiaoli.xiaoliadminservice.user.service.IAppUserService;
import org.xiaoli.xiaolicommoncore.utils.AESUtil;
import org.xiaoli.xiaolicommondomain.domain.ResultCode;
import org.xiaoli.xiaolicommondomain.exception.ServiceException;



@Slf4j
@Service
public class AppUserServiceImpl implements IAppUserService {


    @Autowired
    private AppUserMapper appUserMapper;

    @Value("${appuser.info.defaultAvatar}")
    private String defaultAvatar;

    @Autowired
    private RabbitTemplate rabbitTemplate;


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
        appUser.setNickName("肥波的忠实粉"+(int)(Math.random()*9000) + 1000);
        appUser.setPhoneNumber(null);
//      执行插入逻辑
        appUserMapper.insert(appUser);
        AppUserDTO appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser,appUserDTO);
        appUserDTO.setOpenId(openId);

        return appUserDTO;
    }



    /**
     * 根据openid来查询用户信息
     * @param openId 用户微信ID
     * @return C端用户VO
     */
    @Override
    public AppUserDTO findByOpenId(String openId) {

        if(StringUtils.isEmpty(openId)){
            return null;
        }

        AppUser appUser = appUserMapper.selectById(openId);

        if(appUser == null){
            return null;
        }

        AppUserDTO appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser,appUserDTO);
//      处理手机号~~
        appUserDTO.setPhoneNumber(AESUtil.encryptHex(appUser.getPhoneNumber()));
        return appUserDTO;
    }


    /**
     * 根据手机号来查询用户信息
     * @param phoneNumber
     * @return
     */
    @Override
    public AppUserDTO findByPhone(String phoneNumber) {
        if(StringUtils.isEmpty(phoneNumber)){
            return null;
        }

       AppUser appUser= appUserMapper.selectOne(new LambdaQueryWrapper<AppUser>().eq(AppUser::getPhoneNumber,AESUtil.encryptHex(phoneNumber)));
        if(appUser == null){
            return null;
        }
        AppUserDTO appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser,appUserDTO);

        appUserDTO.setPhoneNumber(AESUtil.decryptHex(appUser.getPhoneNumber()));
        return appUserDTO;
    }

    /**
     * 根据手机号来注册用户信息
     * @param phoneNumber
     * @return
     */
    @Override
    public AppUserDTO registerByPhone(String phoneNumber) {

        /**
         * 判空逻辑
         */
        if(StringUtils.isEmpty(phoneNumber)){
            throw new ServiceException("手机号不能为空", ResultCode.INVALID_PARA.getCode());
        }
//      进行设置默认值
        AppUser appUser = new AppUser();
        appUser.setAvatar(defaultAvatar);
        appUser.setNickName("肥波的忠实粉"+(int)(Math.random()*9000) + 1000);
        appUser.setPhoneNumber(AESUtil.encryptHex(phoneNumber));
//      执行插入逻辑
        appUserMapper.insert(appUser);
        AppUserDTO  appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser,appUserDTO);
        appUserDTO.setUserId(appUser.getId());
        return appUserDTO;
    }


    /**
     * 编辑C端用户
     * @param userEditReqDTO C段用户DTO
     * @return
     */
    @Override
    public void edit(UserEditReqDTO userEditReqDTO) {
        AppUser appUser = appUserMapper.selectById(userEditReqDTO.getUserId());
        if(appUser == null){
            throw new ServiceException("用户不存在",ResultCode.INVALID_PARA.getCode());
        }

        appUser.setNickName(userEditReqDTO.getNickName());
        appUser.setAvatar(userEditReqDTO.getAvatar());
        appUserMapper.updateById(appUser);

//      发送广播消息
        AppUserDTO appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser,appUserDTO);
        appUserDTO.setUserId(appUser.getId());
        try{
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,"",appUserDTO);
//                                        交换机名称                    广播模式      消息内容：用户信息
        }catch (Exception e){
            log.error("编辑用户发送消息失败",e);
        }


    }
}
