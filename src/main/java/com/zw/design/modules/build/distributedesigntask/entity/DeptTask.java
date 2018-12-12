package com.zw.design.modules.build.distributedesigntask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.utils.Const;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class DeptTask implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer departmentType; // 科室类型 1，机械（7个）  2，液压（2个）  3，电气（3个）  4，软件（1个）
    private Integer departmentNum; // 科室编号
    private Integer stepNum; // 执行顺序
    private Integer stepNo; // 步骤编号
    private String stepName; // 步骤名称
    @Transient
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startTime; // 开始时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endTime; // 结束时间
    private Integer status = 0; // 状态 0：未开始  1：执行中  2：已完成 100+：删除
    private String comment; // 备注
    private String principal;// 负责人
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date planStartTime; // 预计完成时间
    private String annotate; // 领导批注
    @ManyToOne
    @JsonIgnoreProperties("deptTasks")
    Project project;

    public String getName() {
        return Const.DEPARTMENT_NAME[departmentType] + Const.DEPARTMENT_NUM[departmentNum];
    }

}
