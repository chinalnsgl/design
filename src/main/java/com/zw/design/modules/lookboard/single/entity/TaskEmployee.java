package com.zw.design.modules.lookboard.single.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zw.design.modules.baseinfosetting.emp.entity.Employee;
import com.zw.design.modules.build.distributedesigntask.entity.Task;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 生产任务负责人表
 */
@Entity
@Getter
@Setter
public class TaskEmployee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee; // 责任人

    private String empName; // 负责人

    private Integer orderNo = 1; // 排序 (预留字段)

    private String content; // 负责内容

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startTime; // 开始时间

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endTime; // 结束时间

    private Integer status = 1; // 状态  0：删除  1：正常

    private String comment; // 备注

    private Float duration; // 历时（天数）

    @ManyToOne
    @JsonIgnoreProperties("employees")
    Task task;

}
