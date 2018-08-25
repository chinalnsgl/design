package com.zw.design.service;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.SysPermission;
import com.zw.design.query.PermissionQuery;

import java.util.List;

public interface PermissionService {

    SysPermission savePermission(SysPermission sysPermission);

    DataTablesCommonDto<SysPermission> findPermissionByCriteria(PermissionQuery query);

    SysPermission updatePermissionStatus(Integer id, Integer status);

    SysPermission findByPermissionName(String permissionName);

    List<SysPermission> findPermissionAll();

    SysPermission findbyId(Integer id);

    SysPermission updatePermission(SysPermission permission);

    List<SysPermission> findPermissionByRoleId(Integer id);
}
