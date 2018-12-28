package com.zw.design.modules.build.distributedesigntask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zw.design.modules.baseinfosetting.section.entity.Section;
import com.zw.design.modules.baseinfosetting.taskname.entity.TaskName;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.lookboard.single.entity.Image;
import com.zw.design.modules.lookboard.single.entity.TaskEmployee;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 任务表
 */
@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sectionId")
    private Section section; // 科室名称

    @ManyToOne
    @JoinColumn(name = "taskNameId")
    private TaskName taskName; // 任务名称

    private String alias; // 任务别名

    private String designDepts; // 参与科室

    @OneToMany(mappedBy = "task",fetch = FetchType.EAGER)
    @OrderBy("orderNo")
    @Where(clause = "status = 1")
    @JsonIgnoreProperties("task")
    private List<TaskEmployee> employees;// 负责人

    private Integer taskOrder; // 任务执行顺序（预留字段）

    private Integer orderNo = 1; // 排序 (预留字段)

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startTime; // 开始时间

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endTime; // 结束时间

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date planFinishTime; // 预计完成时间

    private Integer compStatus = 0; // 执行状态 0：未开始  1：执行中  2：已完成

    private Integer status = 1; // 任务状态  0：删除  1：正常

    private String comment; // 备注

    private String contractNo; // 合同号

    private Integer config; // 配置 0：排除   1：设计   2：工艺   3：生产

    @ManyToOne
    @JsonIgnoreProperties("tasks")
    Project project;

    @OneToMany(mappedBy = "task")
    @Where(clause = "status = 1")
    @JsonIgnoreProperties("task")
    private List<Image> images;// 文件
}
