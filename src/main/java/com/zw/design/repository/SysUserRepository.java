package com.zw.design.repository;

import com.zw.design.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysUserRepository extends JpaRepository<SysUser, Integer>, JpaSpecificationExecutor<SysUser> {

    /**
     * 根据用户名查用户
     * @param username
     * @return
     */
    SysUser findByUserNameAndStatusGreaterThanEqual(String username, Integer status);
}
