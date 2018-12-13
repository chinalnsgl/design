package com.zw.design.modules.build.create.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zw.design.modules.build.distributedesigntask.entity.*;
import com.zw.design.modules.baseinfosetting.projecttype.entity.ProjectType;
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

    private String code; // 项目号

    private String codeSpecial; // 任务单号

    private String name; // 项目名称

    private String address; // 安装地点

    private String demander; // 甲方

    private Integer num; // 数量

    private String comment; // 备注

    private Integer status = 1; // 状态 0：取消项目   1：未下达任务单   2：已下达任务单   3：暂停项目   4,已完成

    private Integer projectClassify = 1; // 项目分类 1：设计类   2：非设计类

    private Integer preStatus; // 修改状态操作之前状态值

    private Integer orderNo = 0; // 优先排序

    private String principal; // 委托人

    private String tel; // 联系方式

    private String designDepts; // 设计任务参与部门

    private String produceDepts; // 生产任务参与部门

    private String processDepts; // 工艺任务参与部门

    private Integer designTaskStatus = 0; // 设计任务状态  0：未开始  1：执行中   2：已完成

    private Integer processTaskStatus = 0; // 工艺任务状态  0：未开始  1：执行中   2：已完成

    private Integer produceTaskStatus = 0; // 生产任务状态  0：未开始  1：执行中   2：已完成

    private Integer projectTaskStatus = 1; // 项目任务状态  0：未开始  1：执行中   2：已完成

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planEndTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime = new Date(); // 创建时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planTime; // 项目计划完成时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date designPlanTime; // 设计计划完成时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date producePlanTime; // 生产计划完成时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date completeTime; // 完成时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date deliveryDate; // 交货日期

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date designCompleteTime; // 设计任务完成时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date produceCompleteTime; // 生产任务完成时间

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date processCompleteTime; // 工艺任务完成时间

    @OneToMany(mappedBy = "project")
    @OrderBy("orderNo")
    @JsonIgnoreProperties("project")
    List<Task> tasks; // 任务

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties("project")
    List<Image> images; // 项目文件

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties("project")
    List<Message> messages; // 项目互动消息

    @ManyToOne
    @JoinColumn(name = "projectTypeId")
    @JsonIgnoreProperties("projects")
    private ProjectType projectType; // 所属类型

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties("project")
    List<ProjectTaskList> projectTaskLists; // 项目任务单

}
