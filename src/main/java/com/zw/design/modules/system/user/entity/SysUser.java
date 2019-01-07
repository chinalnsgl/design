package com.zw.design.modules.system.user.entity;

import com.zw.design.modules.system.role.entity.SysRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 系统 用户表
 */
@Entity
@Getter
@Setter
public class SysUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String userName; // 帐号

    private String name; // 真实姓名

    @Column(nullable = false)
    private String password; // 密码

    private Integer status = 1; // 0: 锁定  1: 正常

    private String img; // 头像URI

    @ManyToMany(fetch = FetchType.EAGER)
    @Where(clause = "status > 0")
    private List<SysRole> roles; // 包含角色

}
