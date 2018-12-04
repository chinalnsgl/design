package com.zw.design.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 系统 角色表
 */
@Entity
@Getter
@Setter
public class SysRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String roleName; // 角色名称

    private String roleCode; // 角色编码 （用于权限控制）

    private Integer status = 1; // 基础状态 0：删除  1：正常

    private Integer orderNo; // 排序（保留字段）

    @Transient
    private boolean checkFlag = false; // 冗余字段， 非数据库映射字段， 用于设置当前登录用户所拥有的角色标记

    @ManyToOne
    @JoinColumn(name = "parentId")
    @JsonIgnoreProperties("children")
    private SysRole parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @Where(clause = "status = 1")
    @JsonIgnoreProperties("parent")
    private List<SysRole> children;

    @ManyToMany(fetch = FetchType.EAGER)
    @Where(clause = "status = 1")
    @OrderBy("orderNo")
    private List<SysPermission> permissions;
}
