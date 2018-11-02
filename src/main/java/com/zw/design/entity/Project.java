package com.zw.design.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderNo = 0; // 优先排序
    private String code; // 项目号
    private String codeSpecial; // 特种项目号
    private String name; // 项目名称
    private String address; // 安装地点
    private String demander; // 甲方
    private Integer num; // 数量
    private String comment; // 备注
    private Integer status = 1; // 状态 0：取消项目  1：未下达任务单  2：已下达任务单  3：暂停项目 4,已完成
    private Integer preStatus;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date planStartTime; // 计划开始时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date planEndTime; // 计划结束时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime = new Date(); // 创建时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date planTime = new Date(); // 计划完成时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date completeTime; // 完成时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date deliveryDate; // 交货日期

    @OneToMany(mappedBy = "project")
    @OrderBy("departmentType,id")
    @JsonIgnoreProperties("project")
    List<DeptTask> deptTasks;

    @OneToMany(mappedBy = "project")
    @OrderBy("produceNum,id")
    @JsonIgnoreProperties("project")
    List<ProduceTask> produceTasks;

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties("project")
    List<Image> images;


}
