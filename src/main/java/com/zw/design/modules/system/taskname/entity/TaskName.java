package com.zw.design.modules.system.taskname.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zw.design.modules.system.sectiontype.entity.SectionType;
import com.zw.design.modules.system.tasktype.entity.TaskType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 员工表
 */
@Entity
@Getter
@Setter
public class TaskName implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name; // 名称

    private int status = 1; // 基础状态 0：删除  1：正常

    private Integer orderNo = 1; // 排序（保留字段）

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime = new Date(); // 创建时间

    @ManyToOne
    @JoinColumn(name = "taskTypeId")
    @JsonIgnoreProperties("taskNames")
    private TaskType taskType; // 所属任务类型

    @ManyToOne
    @JoinColumn(name = "sectionTypeId")
    @JsonIgnoreProperties("taskNames")
    private SectionType sectionType; // 所属任务类型

}
