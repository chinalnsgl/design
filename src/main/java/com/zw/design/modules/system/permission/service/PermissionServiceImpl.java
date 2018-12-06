package com.zw.design.modules.system.permission.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.permission.entity.SysPermission;
import com.zw.design.modules.system.permission.query.PermissionQuery;
import com.zw.design.modules.system.permission.repository.SysPermissionRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private SysPermissionRepository permissionRepository;

    // 按条件查询权限表格模型数据
    @Override
    public BaseDataTableModel<SysPermission> findPermissionByQuery(PermissionQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(),Sort.by("orderNo"));
        Page<SysPermission> permissionPage = permissionRepository.findAll((Specification<SysPermission>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (null != query.getPermissionName() && !"".equals(query.getPermissionName())) {
                list.add(criteriaBuilder.like(root.get("permissionName").as(String.class), "%" + query.getPermissionName() + "%"));
            }
            list.add(criteriaBuilder.isNull(root.get("parent")));
            list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        BaseDataTableModel<SysPermission> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(permissionPage.getContent());
        baseDataTableModel.setRecordsTotal((int)permissionPage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)permissionPage.getTotalElements());
        return baseDataTableModel;
    }

    // 查询所有权限
    @Override
    public List<SysPermission> findPermissionAll() {
        return permissionRepository.findAll((Specification<SysPermission>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        },Sort.by("orderNo"));
    }

    // 按ID查询权限
    @Override
    public SysPermission findById(Integer id) {
        return permissionRepository.findById(id).get();
    }

    // 按权限名称查询
    @Override
    public SysPermission findByPermissionName(String permissionName) {
        return permissionRepository.findByPermissionNameAndStatus(permissionName, 1);
    }

    // 保存权限
    @Override
    public SysPermission savePermission(SysPermission sysPermission) {
        if (sysPermission.getParent() != null && sysPermission.getParent().getId() == null) {
            sysPermission.setParent(null);
        }
        return permissionRepository.save(sysPermission);
    }

    // 修改权限
    @Override
    public SysPermission updatePermission(SysPermission permission) {
        SysPermission permission1 = findById(permission.getId());
        permission1.setDescription(permission.getDescription());
        permission1.setUrl(permission.getUrl());
        permission1.setPermissionName(permission.getPermissionName());
        permission1.setOrderNo(permission.getOrderNo());
        return permissionRepository.saveAndFlush(permission1);
    }

    // 修改权限状态
    @Override
    public SysPermission updatePermissionStatus(Integer id, Integer status) {
        SysPermission permission = findById(id);
        permission.setStatus(status);
        return permissionRepository.save(permission);
    }
}
