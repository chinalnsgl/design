package com.zw.design.service.impl;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.SysPermission;
import com.zw.design.entity.SysRole;
import com.zw.design.query.RoleQuery;
import com.zw.design.repository.SysRoleRepository;
import com.zw.design.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public DataTablesCommonDto<SysRole> findRoleByCriteria(RoleQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(), Sort.by(Sort.Direction.ASC,"roleName"));
        Page<SysRole> rolePage = sysRoleRepository.findAll((Specification<SysRole>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            if (null != query.getRoleName() && !"".equals(query.getRoleName())) {
                list.add(criteriaBuilder.like(root.get("roleName").as(String.class), "%" + query.getRoleName() + "%"));
            }
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        DataTablesCommonDto<SysRole> dataTablesCommonDto = new DataTablesCommonDto<>();
        dataTablesCommonDto.setDraw(query.getDraw());
        dataTablesCommonDto.setData(rolePage.getContent());
        dataTablesCommonDto.setRecordsTotal((int)rolePage.getTotalElements());
        dataTablesCommonDto.setRecordsFiltered((int)rolePage.getTotalElements());
        return dataTablesCommonDto;
    }

    @Override
    public SysRole updateRoleStatus(Integer id,Integer status) {
        SysRole role = sysRoleRepository.findById(id).get();
        role.setStatus(status);
        return sysRoleRepository.save(role);
    }

    @Override
    public SysRole saveRole(SysRole role, Integer[] permissions) {
        if (permissions != null) {
            List<SysPermission> permissionList = new ArrayList<>();
            for (Integer integer : permissions) {
                SysPermission permission = new SysPermission();
                permission.setId(integer);
                permissionList.add(permission);
            }
            role.setPermissions(permissionList);
        }
        return sysRoleRepository.save(role);
    }

    @Override
    public SysRole findByRoleName(String roleName) {
        return sysRoleRepository.findByRoleNameAndStatus(roleName,1);
    }

    @Override
    public List<SysRole> findAll() {
        List<SysRole> roles = sysRoleRepository.findAll((Specification<SysRole>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status").as(Integer.class), 1), Sort.by(Sort.Direction.ASC, "roleName"));
        return roles;
    }

    @Override
    public SysRole findRoleById(Integer id) {
        return sysRoleRepository.findById(id).get();
    }

    @Override
    public SysRole updateRole(SysRole role, Integer[] permissions) {
        SysRole sysRole = sysRoleRepository.findById(role.getId()).get();
        sysRole.getPermissions().removeAll(sysRole.getPermissions());
//        sysRole.setRoleName(role.getRoleName());
        if (permissions != null) {
            List<SysPermission> permissionList = new ArrayList<>();
            for (Integer integer : permissions) {
                SysPermission permission = new SysPermission();
                permission.setId(integer);
                permissionList.add(permission);
            }
            sysRole.setPermissions(permissionList);
        }
        return sysRoleRepository.save(sysRole);
    }
}
