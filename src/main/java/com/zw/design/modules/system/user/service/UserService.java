package com.zw.design.modules.system.user.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.role.entity.SysRole;
import com.zw.design.modules.system.user.entity.SysUser;
import com.zw.design.modules.system.user.query.UserQuery;

import java.util.List;

public interface UserService {

    // 按条件查询用户表格模型数据
    BaseDataTableModel<SysUser> findUserByQuery(UserQuery query);

    // 按用户名查询用户
    SysUser findByUserNameAndStatusGreaterThanEqual(String userName, Integer status);

    // 根据状态查询用户
    List<SysUser> findAllByStatus();

    // 按用户ID查询用户角色
    List<SysRole> findUserRoleById(Integer id);

    // 保存用户
    SysUser saveUser(SysUser user, Integer[] role);

    // 修改用户
    SysUser updateUser(SysUser user, Integer[] role);

    // 修改密码
    SysUser updateUserPassword(SysUser sysUser);

    // 修改用户头像
    SysUser updateImage(String s);

    // 修改用户状态
    SysUser updateUserStatus(Integer id, Integer status);

}
