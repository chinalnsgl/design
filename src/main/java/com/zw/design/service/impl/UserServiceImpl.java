package com.zw.design.service.impl;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.SysRole;
import com.zw.design.entity.SysUser;
import com.zw.design.query.UserQuery;
import com.zw.design.repository.SysRoleRepository;
import com.zw.design.repository.SysUserRepository;
import com.zw.design.service.UserService;
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
        if (role != null) {
            List<SysRole> roles = new ArrayList<>();
            for (Integer integer : role) {
                SysRole sysRole = new SysRole();
                sysRole.setId(integer);
                roles.add(sysRole);
            }
            user.setRoles(roles);
        }
        return sysUserRepository.save(user);
    }

    @Override
    public SysUser updateUser(SysUser user, Integer[] role) {
        SysUser sysUser = sysUserRepository.findById(user.getId()).get();
        sysUser.setName(user.getName());
        sysUser.getRoles().removeAll(sysUser.getRoles());
        if (role != null) {
            List<SysRole> roles = new ArrayList<>();
            for (Integer integer : role) {
                SysRole sysRole = new SysRole();
                sysRole.setId(integer);
                roles.add(sysRole);
            }
            sysUser.setRoles(roles);
        }
        return sysUserRepository.saveAndFlush(sysUser);
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
    public DataTablesCommonDto<SysUser> findUserByCriteria(UserQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength());
        Page<SysUser> userPage = sysUserRepository.findAll((Specification<SysUser>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
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
        DataTablesCommonDto<SysUser> dataTablesCommonDto = new DataTablesCommonDto<>();
        dataTablesCommonDto.setDraw(query.getDraw());
        dataTablesCommonDto.setData(userPage.getContent());
        dataTablesCommonDto.setRecordsTotal((int)userPage.getTotalElements());
        dataTablesCommonDto.setRecordsFiltered((int)userPage.getTotalElements());
        return dataTablesCommonDto;
    }

    @Override
    public SysUser updateUserStatus(Integer id, Integer status) {
        SysUser user = sysUserRepository.findById(id).get();
        user.setStatus(status);
        return sysUserRepository.save(user);
    }

    @Override
    public SysUser findUserRoleById(Integer id) {
        List<SysRole> roles = sysRoleRepository.findAllByStatus(1);
        SysUser user = sysUserRepository.findById(id).get();
        for (SysRole role : roles) {
            for (SysRole userRole : user.getRoles()) {
                if (role.getId() == userRole.getId()) {
                    role.setCheckFlag(true);
                }
            }
        }
        user.setRoles(roles);
        return user;
    }
}
