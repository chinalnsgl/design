package com.zw.design.modules.system.role.repository;

import com.zw.design.modules.system.role.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SysRoleRepository extends JpaRepository<SysRole, Integer>, JpaSpecificationExecutor<SysRole> {

    SysRole findByRoleNameAndStatus(String roleName,Integer status);

    List<SysRole> findAllByStatus(Integer status);
}
