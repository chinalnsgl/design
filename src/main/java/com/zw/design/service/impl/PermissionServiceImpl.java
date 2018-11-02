package com.zw.design.service.impl;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.SysPermission;
import com.zw.design.entity.SysRole;
import com.zw.design.query.PermissionQuery;
import com.zw.design.repository.SysPermissionRepository;
import com.zw.design.repository.SysRoleRepository;
import com.zw.design.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private SysPermissionRepository permissionRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public SysPermission savePermission(SysPermission sysPermission) {
        if (sysPermission.getParent() != null && sysPermission.getParent().getId() == null) {
            sysPermission.setParent(null);
        }
        return permissionRepository.save(sysPermission);
    }

    @Override
    public DataTablesCommonDto<SysPermission> findPermissionByCriteria(PermissionQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(),Sort.by("orderNo"));
        Page<SysPermission> permissionPage = permissionRepository.findAll((Specification<SysPermission>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            if (null != query.getPermissionName() && !"".equals(query.getPermissionName())) {
                list.add(criteriaBuilder.like(root.get("permissionName").as(String.class), "%" + query.getPermissionName() + "%"));
            }
            list.add(criteriaBuilder.isNull(root.get("parent")));
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        DataTablesCommonDto<SysPermission> dataTablesCommonDto = new DataTablesCommonDto<>();
        dataTablesCommonDto.setDraw(query.getDraw());
        dataTablesCommonDto.setData(permissionPage.getContent());
        dataTablesCommonDto.setRecordsTotal((int)permissionPage.getTotalElements());
        dataTablesCommonDto.setRecordsFiltered((int)permissionPage.getTotalElements());
        return dataTablesCommonDto;
    }

    @Override
    public SysPermission updatePermissionStatus(Integer id, Integer status) {
        SysPermission permission = permissionRepository.findById(id).get();
        permission.setStatus(status);
        return permissionRepository.save(permission);
    }

    @Override
    public SysPermission findByPermissionName(String permissionName) {
        return permissionRepository.findByPermissionNameAndStatus(permissionName, 1);
    }

    @Override
    public List<SysPermission> findPermissionAll() {
        List<SysPermission> permissionList = permissionRepository.findAll((Specification<SysPermission>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
//            list.add(criteriaBuilder.isNull(root.get("parent")));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        },Sort.by("orderNo"));
        return permissionList;
    }

    @Override
    public SysPermission findbyId(Integer id) {
        return permissionRepository.findById(id).get();
    }

    @Override
    public SysPermission updatePermission(SysPermission permission) {
        SysPermission permission1 = permissionRepository.findById(permission.getId()).get();
        permission1.setDescription(permission.getDescription());
        permission1.setUrl(permission.getUrl());
        permission1.setPermissionName(permission.getPermissionName());
//        permission1.getParent();
        return permissionRepository.saveAndFlush(permission1);
    }

    @Override
    public List<SysPermission> findPermissionByRoleId(Integer id) {
        List<SysPermission> permissionList = permissionRepository.findAll((Specification<SysPermission>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            list.add(criteriaBuilder.notEqual(root.get("id").as(Integer.class), 3));
            list.add(criteriaBuilder.notEqual(root.get("id").as(Integer.class), 6));
            list.add(criteriaBuilder.notEqual(root.get("id").as(Integer.class), 7));
            list.add(criteriaBuilder.notEqual(root.get("id").as(Integer.class), 19));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, Sort.by("orderNo"));
//        List<SysPermission> permissionList = findPermissionAll();
        SysRole role = sysRoleRepository.findById(id).get();
        for (SysPermission permission : permissionList) {
            for (SysPermission p : role.getPermissions()) {
                if (p.getId() == permission.getId()) {
                    permission.setCheckFlag(true);
                }
            }
        }
        return permissionList;
    }
}
