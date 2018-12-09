package com.zw.design.modules.system.role.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.permission.entity.SysPermission;
import com.zw.design.modules.system.role.entity.SysRole;
import com.zw.design.modules.system.role.query.RoleQuery;

import java.util.List;

public interface RoleService {

    // 按条件查询角色表格模型数据
    @Deprecated
    BaseDataTableModel<SysRole> findRoleByQuery(RoleQuery query);

    // 按状态查询所有角色
    List<SysRole> findAllByStatus();

    // 按ID查询角色
    SysRole findRoleById(Integer id);

    // 按角色名和状态查询角色
    SysRole findByRoleNameAndStatus(String roleName, Integer status);

    // 按角色ID查询权限集合
    List<SysPermission> findRolePermissionById(Integer id);

    // 保存角色
    SysRole saveRole(SysRole role, Integer[] permissions);

    // 修改角色
    SysRole updateRole(SysRole role, Integer[] permissions);

    // 修改角色状态
    SysRole updateRoleStatus(Integer id, Integer status);
}
