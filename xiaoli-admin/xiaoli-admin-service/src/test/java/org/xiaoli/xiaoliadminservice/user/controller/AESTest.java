package org.xiaoli.xiaoliadminservice.user.controller;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xiaoli.xiaoliadminservice.XiaoliAdminServiceApplication;
import org.xiaoli.xiaolicommoncore.utils.AESUtil;

@SpringBootTest(classes = XiaoliAdminServiceApplication.class)
public class AESTest {

    @Test
    void testEncrypt(){
        String password = "123666";

        System.out.println(AESUtil.encryptHex(password));
        System.out.println(AESUtil.decryptHex(AESUtil.encryptHex(password)));
    }
}
