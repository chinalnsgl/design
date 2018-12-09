package com.zw.design.modules.system.permission.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.permission.entity.SysPermission;
import com.zw.design.modules.system.permission.query.PermissionQuery;

import java.util.List;

public interface PermissionService {

    // 按条件查询权限表格模型数据
    BaseDataTableModel<SysPermission> findPermissionByQuery(PermissionQuery query);

    // 查询所有权限
    List<SysPermission> findPermissionAll();

    // 按ID查询权限
    SysPermission findById(Integer id);

    // 按权限名称查询
    SysPermission findByPermissionNameAndStatus(String permissionName, Integer status);

    // 保存权限
    SysPermission savePermission(SysPermission sysPermission);

    // 修改权限
    SysPermission updatePermission(SysPermission permission);

    // 修改权限状态
    SysPermission updatePermissionStatus(Integer id, Integer status);
}
