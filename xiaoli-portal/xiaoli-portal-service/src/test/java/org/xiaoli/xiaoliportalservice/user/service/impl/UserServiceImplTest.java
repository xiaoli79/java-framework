package org.xiaoli.xiaoliportalservice.user.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xiaoli.xiaolicommonmessage.service.AliPnsService;
import org.xiaoli.xiaoliportalservice.user.service.IUserService;

/**
 * C端用户服务单元测试
 */
@SpringBootTest
class UserServiceImplTest {




    @Autowired
    private AliPnsService aliPnsService;



    @Test
    public void sendMessage(){
        aliPnsService.sendSmsVerifyCode("17337191448");
    }



//    @Autowired
//    private AliSmsService aliSmsService;
//
//
//    @Test
//    void sendMessage(){
//
//
//        aliSmsService.sendMobileCode("17337191448","666");
//
//    }













    @Test
    void login() {
    }
}