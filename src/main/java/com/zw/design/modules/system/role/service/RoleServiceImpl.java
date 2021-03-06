package com.zw.design.modules.system.role.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.log.service.LogService;
import com.zw.design.modules.system.permission.entity.SysPermission;
import com.zw.design.modules.system.role.entity.SysRole;
import com.zw.design.modules.system.role.query.RoleQuery;
import com.zw.design.modules.system.permission.repository.SysPermissionRepository;
import com.zw.design.modules.system.role.repository.SysRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private SysPermissionRepository sysPermissionRepository;
    @Autowired
    private LogService logService;

    // 构建权限集合
    private List<SysPermission> createPermissions(Integer[] permissions) {
        if (permissions != null) {
            List<SysPermission> permissionList = new ArrayList<>();
            for (Integer integer : permissions) {
                SysPermission permission = new SysPermission();
                permission.setId(integer);
                permissionList.add(permission);
            }
            return permissionList;
        }
        return null;
    }

    // 按条件查询角色表格模型数据
    @Override
    @Deprecated
    public BaseDataTableModel<SysRole> findRoleByQuery(RoleQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(), Sort.by(Sort.Direction.ASC,"roleName"));
        Page<SysRole> rolePage = sysRoleRepository.findAll((Specification<SysRole>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (null != query.getRoleName() && !"".equals(query.getRoleName())) {
                list.add(criteriaBuilder.like(root.get("roleName").as(String.class), "%" + query.getRoleName() + "%"));
            }
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        BaseDataTableModel<SysRole> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(rolePage.getContent());
        baseDataTableModel.setRecordsTotal((int)rolePage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)rolePage.getTotalElements());
        return baseDataTableModel;
    }

    // 按状态查询所有角色
    @Override
    public List<SysRole> findAllByStatus() {
        return sysRoleRepository.findAll((Specification<SysRole>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), 1), Sort.by("orderNo"));
    }

    // 按ID查询角色
    @Override
    public SysRole findRoleById(Integer id) {
        return sysRoleRepository.findById(id).get();
    }

    // 按角色名和状态查询角色
    @Override
    public SysRole findByRoleNameAndStatus(String roleName, Integer status) {
        return sysRoleRepository.findByRoleNameAndStatus(roleName,status);
    }

    // 按角色ID查询权限集合
    @Override
    public List<SysPermission> findRolePermissionById(Integer id) {
        List<SysPermission> permissionList = sysPermissionRepository.findAll((Specification<SysPermission>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, Sort.by("orderNo"));
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

    // 保存角色
    @Override
    public SysRole saveRole(SysRole role, Integer[] permissions) {
        if (role.getParent() != null && role.getParent().getId() == null) {
            role.setParent(null);
        }
        role.setPermissions(createPermissions(permissions));
        logService.saveLog("新建角色" , role.getRoleName());
        return sysRoleRepository.save(role);
    }

    // 修改角色
    @Override
    public SysRole updateRole(SysRole role, Integer[] permissions) {
        SysRole sysRole = sysRoleRepository.findById(role.getId()).get();
        logService.saveLog("修改角色", sysRole, role);
        sysRole.setRoleName(role.getRoleName());
        sysRole.getPermissions().removeAll(sysRole.getPermissions());
        sysRole.setOrderNo(role.getOrderNo());
        sysRole.setPermissions(createPermissions(permissions));
        return sysRoleRepository.save(sysRole);
    }

    // 修改角色状态
    @Override
    public SysRole updateRoleStatus(Integer id,Integer status) {
        SysRole role = sysRoleRepository.findById(id).get();
        logService.saveLog("删除角色" , role.getRoleName());
        role.setStatus(status);
        return sysRoleRepository.save(role);
    }
}
