package org.xiaoli.xiaoliportalservice.user.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xiaoli.xiaolicommonmessage.service.AliPnsService;

/**
 * C端用户服务单元测试
 */
@SpringBootTest
class UserServiceImplTest {




    @Autowired
    private AliPnsService aliPnsService;


    @Autowired
    private CaptchaService captchaService;



    @Test
    public void sendMessage(){
        aliPnsService.sendSmsVerifyCode("17337191448");
    }


    @Test
    public void sendPnsVerifyCode(){
        captchaService.sendCode("17337191448");
    }
}