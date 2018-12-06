package com.zw.design.modules.system.user.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.role.entity.SysRole;
import com.zw.design.modules.system.user.entity.SysUser;
import com.zw.design.modules.system.user.query.UserQuery;

import java.util.List;

public interface UserService {

    SysUser findByUserName(String userName);

    SysUser saveUser(SysUser user, Integer[] role);

    BaseDataTableModel<SysUser> findUserByCriteria(UserQuery query);

    SysUser updateUserStatus(Integer id, Integer status);

    List<SysRole> findUserRoleById(Integer id);

    SysUser updateUser(SysUser user, Integer[] role);

    SysUser updateUser(SysUser sysUser);

    List<SysUser> findUserList();

    SysUser updateImage(String s);
}
