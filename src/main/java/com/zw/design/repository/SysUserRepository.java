package com.zw.design.repository;

import com.zw.design.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SysUserRepository extends JpaRepository<SysUser, Integer>, JpaSpecificationExecutor<SysUser> {

    SysUser findByUserNameAndStatusGreaterThanEqual(String username, Integer status);

    List<SysUser> findByStatus(Integer status);

    SysUser findByName(String name);
}
