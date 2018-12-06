package com.zw.design.modules.build.distribute.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zw.design.modules.build.create.entity.Project;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name; // 文件名
    private String url; // 文件路径
    private Integer status = 1; // 0：删除  1：正常
    private String comment; // 备注
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date uploadTime; // 上传时间
    private String userId;
    private String userName;
    private Integer type; // 1,任务单 2，技术协议 3，合同
    @ManyToOne
    @JoinColumn(name = "projectId")
    @JsonIgnoreProperties("images")
    private Project project;

}
