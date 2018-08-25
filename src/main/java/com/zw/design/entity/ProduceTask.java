package com.zw.design.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class ProduceTask implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String produceName; // 生产任务名称
    private Integer produceNum; // 生产任务序号
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startTime; // 开始时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endTime; // 结束时间
    private Integer status = 0; // 状态 0：未开始  1：执行中  2：已完成
    private String comment; // 备注
    private String principal;// 负责人
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date planStartTime; // 预计完成时间
    private String annotate; // 领导批注
    @ManyToOne
    @JsonIgnoreProperties("produceTasks")
    Project project;
}
