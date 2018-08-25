package com.zw.design.repository;

import com.zw.design.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SysPermissionRepository extends JpaRepository<SysPermission, Integer>, JpaSpecificationExecutor<SysPermission> {

    SysPermission findByPermissionNameAndStatus(String permissionName,Integer status);
}
