package org.xiaoli.xiaoliportalservice.user.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaoli.xiaolicommondomain.domain.R;
import org.xiaoli.xiaolicommondomain.domain.vo.TokenVO;
import org.xiaoli.xiaoliportalservice.user.entity.dto.WeChatLoginDTO;
import org.xiaoli.xiaoliportalservice.user.service.IUserService;

/**
 * 门户程序用户的入口
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {



    @Autowired
    IUserService userService;


    /**
     * 微信登录
     * @param weChatLoginDTO 微信登录DTO
     * @return
     */
    @PostMapping("/login/wechat")
    public R<TokenVO>  login (@RequestBody @Validated WeChatLoginDTO weChatLoginDTO){

        return R.ok(userService.login(weChatLoginDTO).convertTokenVO());
    }




}
