package com.zw.design.service;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.SysUser;
import com.zw.design.query.UserQuery;

public interface UserService {

    SysUser findByUserName(String userName);

    SysUser saveUser(SysUser user, Integer[] role);

    DataTablesCommonDto<SysUser> findUserByCriteria(UserQuery query);

    SysUser updateUserStatus(Integer id, Integer status);

    SysUser findUserRoleById(Integer id);

    SysUser updateUser(SysUser user, Integer[] role);

    SysUser updateUser(SysUser sysUser);
}
