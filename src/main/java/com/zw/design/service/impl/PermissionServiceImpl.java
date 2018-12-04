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
            List<Predicate> list = new ArrayList<>();
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
        return permissionRepository.findAll((Specification<SysPermission>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        },Sort.by("orderNo"));
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
        permission1.setOrderNo(permission.getOrderNo());
        return permissionRepository.saveAndFlush(permission1);
    }
}
