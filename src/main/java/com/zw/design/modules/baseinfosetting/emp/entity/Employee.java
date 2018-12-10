package com.zw.design.modules.baseinfosetting.emp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zw.design.modules.baseinfosetting.dept.entity.Department;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 员工表
 */
@Entity
@Getter
@Setter
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name; // 名称

    private String code; // 编号

    private int status = 1; // 基础状态 0：删除  1：正常

    private Integer orderNo = 1; // 排序（保留字段）

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime = new Date(); // 创建时间

    @ManyToOne
    @JoinColumn(name = "deptId")
    @JsonIgnoreProperties("employees")
    private Department department; // 所属部门

}
