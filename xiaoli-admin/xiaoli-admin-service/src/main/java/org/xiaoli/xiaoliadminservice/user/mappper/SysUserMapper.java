package org.xiaoli.xiaoliadminservice.user.mappper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xiaoli.xiaoliadminservice.user.domain.entity.SysUser;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> selectList(SysUser sysUser);




}
