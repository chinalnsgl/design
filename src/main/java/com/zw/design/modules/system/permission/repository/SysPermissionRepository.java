package com.zw.design.modules.system.permission.repository;

import com.zw.design.modules.system.permission.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysPermissionRepository extends JpaRepository<SysPermission, Integer>, JpaSpecificationExecutor<SysPermission> {

    SysPermission findByPermissionNameAndStatus(String permissionName,Integer status);
}
