package com.zw.design.modules.build.distributedesigntask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zw.design.modules.build.create.entity.Project;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 项目任务单表
 */
@Entity
@Getter
@Setter
public class ProjectTaskList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code; // 任务单号

    private String name; // 项目名称

    private String address; // 安装地点

    private String demander; // 甲方

    private Integer num; // 数量

    private String comment; // 备注

    private Integer status = 1; // 状态 0：取消项目   1：未下达任务单   2：已下达任务单   3：暂停项目   4,已完成

    private Integer orderNo = 0; // 优先排序

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime = new Date(); // 创建时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date deliveryDate; // 交货日期

    private String principal; // 委托人

    private String tel; // 联系方式

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date designCompleteTime; // 设计任务完成时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date produceCompleteTime; // 生产任务完成时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date processCompleteTime; // 工艺任务完成时间

    @ManyToOne
    @JsonIgnoreProperties("projectTaskLists")
    Project project;
}
