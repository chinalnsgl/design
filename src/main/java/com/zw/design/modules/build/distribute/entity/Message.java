package com.zw.design.modules.build.distribute.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.system.user.entity.SysUser;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content; // 内容

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties("messages")
    private SysUser user; // 消息发送用户

    private Integer status = 1; // 状态
    private String users; // 接收消秘的用户名
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = new Date();

    @ManyToOne
    @JoinColumn(name = "projectId")
    @JsonIgnoreProperties("messages")
    private Project project;
}
