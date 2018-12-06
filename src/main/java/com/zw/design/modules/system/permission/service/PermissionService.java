package com.zw.design.modules.system.permission.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.permission.entity.SysPermission;
import com.zw.design.modules.system.permission.query.PermissionQuery;

import java.util.List;

public interface PermissionService {

    SysPermission savePermission(SysPermission sysPermission);

    BaseDataTableModel<SysPermission> findPermissionByCriteria(PermissionQuery query);

    SysPermission updatePermissionStatus(Integer id, Integer status);

    SysPermission findByPermissionName(String permissionName);

    List<SysPermission> findPermissionAll();

    SysPermission findbyId(Integer id);

    SysPermission updatePermission(SysPermission permission);
}
