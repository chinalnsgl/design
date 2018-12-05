package com.zw.design.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 自关联部门表
 */
@Entity
@Getter
@Setter
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String deptName; // 部门名称

    private int status = 1; // 基础状态 0：删除  1：正常

    private Integer orderNo = 1; // 排序（保留字段）

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime = new Date(); // 创建时间

    @ManyToOne
    @JoinColumn(name = "parentId")
    @JsonIgnoreProperties("children")
    private Department parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @OrderBy("orderNo")
    @Where(clause = "status = 1")
    @JsonIgnoreProperties("parent")
    private List<Department> children;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    @OrderBy("orderNo")
    @Where(clause = "status = 1")
    @JsonIgnoreProperties("department")
    private List<Employee> employees;

}
