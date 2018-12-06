package com.zw.design.modules.system.role.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.permission.entity.SysPermission;
import com.zw.design.modules.system.role.entity.SysRole;
import com.zw.design.modules.system.role.query.RoleQuery;

import java.util.List;

public interface RoleService {


    BaseDataTableModel<SysRole> findRoleByCriteria(RoleQuery query);

    SysRole updateRoleStatus(Integer id, Integer status);

    SysRole saveRole(SysRole role, Integer[] permissions);

    SysRole findByRoleName(String roleName);

    List<SysRole> findAll();

    SysRole updateRole(SysRole role, Integer[] permissions);

    SysRole findRoleById(Integer id);

    List<SysPermission> findRolePermissionById(Integer id);
}
