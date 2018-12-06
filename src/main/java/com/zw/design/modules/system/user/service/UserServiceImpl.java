package com.zw.design.modules.system.user.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.log.service.LogService;
import com.zw.design.modules.system.role.entity.SysRole;
import com.zw.design.modules.system.user.entity.SysUser;
import com.zw.design.modules.system.user.query.UserQuery;
import com.zw.design.modules.system.role.repository.SysRoleRepository;
import com.zw.design.modules.system.user.repository.SysUserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private LogService logService;

    @Override
    public SysUser findByUserName(String userName) {
        return sysUserRepository.findByUserNameAndStatusGreaterThanEqual(userName,1);
    }

    @Override
    public SysUser saveUser(SysUser user, Integer[] role) {
        String hashAlgorithmName = "MD5";
        String credentials = user.getPassword();
        int hashIterations = 8;
        Object obj = new SimpleHash(hashAlgorithmName, credentials, ByteSource.Util.bytes(user.getUserName()), hashIterations);
        user.setPassword(obj.toString());
        user.setRoles(createRoles(role));
        logService.saveLog("创建用户：", user.getName());
        return sysUserRepository.save(user);
    }

    @Override
    public SysUser updateUser(SysUser user, Integer[] role) {
        SysUser sysUser = sysUserRepository.findById(user.getId()).get();
        logService.saveLog("修改用户：", sysUser, user);
        sysUser.setName(user.getName());
        sysUser.getRoles().removeAll(sysUser.getRoles());
        sysUser.setRoles(createRoles(role));
        return sysUserRepository.saveAndFlush(sysUser);
    }

    private List<SysRole> createRoles(Integer[] role) {
        if (role != null) {
            List<SysRole> roles = new ArrayList<>();
            for (Integer integer : role) {
                SysRole sysRole = new SysRole();
                sysRole.setId(integer);
                roles.add(sysRole);
            }
            return roles;
        }
        return null;
    }

    @Override
    public SysUser updateUser(SysUser sysUser) {
        String hashAlgorithmName = "MD5";
        String credentials = sysUser.getPassword();
        int hashIterations = 8;
        Object obj = new SimpleHash(hashAlgorithmName, credentials, ByteSource.Util.bytes(sysUser.getUserName()), hashIterations);
        sysUser.setPassword(obj.toString());
        return sysUserRepository.saveAndFlush(sysUser);
    }

    @Override
    public BaseDataTableModel<SysUser> findUserByCriteria(UserQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength());
        Page<SysUser> userPage = sysUserRepository.findAll((Specification<SysUser>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (null != query.getUserName() && !"".equals(query.getUserName())) {
                list.add(criteriaBuilder.like(root.get("userName").as(String.class), "%" + query.getUserName() + "%"));
            }
            if (null != query.getName() && !"".equals(query.getName())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getName() + "%"));
            }
            list.add(criteriaBuilder.notEqual(root.get("status").as(Integer.class), 2));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        BaseDataTableModel<SysUser> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(userPage.getContent());
        baseDataTableModel.setRecordsTotal((int)userPage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)userPage.getTotalElements());
        return baseDataTableModel;
    }

    @Override
    public SysUser updateUserStatus(Integer id, Integer status) {
        SysUser user = sysUserRepository.findById(id).get();
        user.setStatus(status);
        logService.saveLog((status == 1 ? "解锁用户：" : "锁定用户：") ,user.getName());
        return sysUserRepository.save(user);
    }

    @Override
    public List<SysRole> findUserRoleById(Integer id) {
        List<SysRole> roles = sysRoleRepository.findAllByStatus(1);
        SysUser user = sysUserRepository.findById(id).get();
        for (SysRole role : roles) {
            for (SysRole userRole : user.getRoles()) {
                if (role.getId() == userRole.getId()) {
                    role.setCheckFlag(true);
                }
            }
        }
        return roles;
    }

    @Override
    public List<SysUser> findUserList() {
        return sysUserRepository.findByStatus(1);
    }

    @Override
    public SysUser updateImage(String s) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        SysUser saveUser = sysUserRepository.findById(user.getId()).get();
        saveUser.setImg(s);
        SysUser u = sysUserRepository.save(saveUser);
        SecurityUtils.getSubject().getSession().setAttribute("user", u);
        return u;
    }
}
