package com.zw.design.service;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.SysRole;
import com.zw.design.query.RoleQuery;

import java.util.List;

public interface RoleService {


    DataTablesCommonDto<SysRole> findRoleByCriteria(RoleQuery query);

    SysRole updateRoleStatus(Integer id, Integer status);

    SysRole saveRole(SysRole role, Integer[] permissions);

    SysRole findByRoleName(String roleName);

    List<SysRole> findAll();

    SysRole updateRole(SysRole role, Integer[] permissions);

    SysRole findRoleById(Integer id);
}
