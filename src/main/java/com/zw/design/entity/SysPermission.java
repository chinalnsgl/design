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
 * 系统 权限表
 */
@Getter
@Setter
@Entity
public class SysPermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String permissionName; // 权限名称

    private String url; // 资源地址URI

    private String description; // 权限描述

    private Integer status = 1; // 基础状态 0：删除  1：正常

    @Transient
    private boolean checkFlag = false; // 冗余字段， 非数据库映射字段， 用于设置当前登录用户所拥有的权限标记

    private Integer orderNo; // 权限排序

    @ManyToOne
    @JoinColumn(name = "pid")
    @JsonIgnoreProperties("children")
    private SysPermission parent; // 自关联表， 权限资源的所属权限

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @OrderBy("orderNo")
    @Where(clause = "status > 1")
    @JsonIgnoreProperties("parent")
    private List<SysPermission> children; // 权限资源包含的有效权限资源
}
