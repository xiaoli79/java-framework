package org.xiaoli.xiaoliadminservice.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.xiaoli.xiaoliadminservice.user.domain.dto.PasswordLoginDTO;
import org.xiaoli.xiaoliadminservice.user.service.ISysUserService;
import org.xiaoli.xiaolicommondomain.domain.R;
import org.xiaoli.xiaolicommondomain.domain.vo.TokenVO;
import org.xiaoli.xiaolicommonsecurity.domain.dto.TokenDTO;

/**
 * B端用户服务控制器类
 */


@RestController
@RequestMapping("/sys_user")
public class SysUserController {




    @Autowired
    private ISysUserService loginService;


    /**
     * B端用户登录
     * @param passwordLoginDTO 入参
     * @return
     */
    @PostMapping("/login")
    public R<TokenVO> login (@Validated @RequestBody PasswordLoginDTO passwordLoginDTO){
        TokenDTO tokenDTO = loginService.login(passwordLoginDTO);
        return R.ok(tokenDTO.convertTokenVO(tokenDTO));
    }

}
