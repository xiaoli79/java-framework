package org.xiaoli.xiaoliadminservice.user.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaoli.xiaolicommoncore.domain.entity.BaseDO;


/**
 * C端用户表对应的实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppUser extends BaseDO {



    /**
     * 用户ID
     */
    private String nickName;


    /**
     * 用户手机号
     */
    private String phoneNumber;


    /**
     * 微信Id
     */
    private String openId;

    /**
     * 用户头像
     */
    private String avatar;






}
