package com.zw.design.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Receiver implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties("messages")
    private SysUser user; // 消息接收人

    private Integer status = 1; // 状态1：未读 2：已读

    @ManyToOne
    @JoinColumn(name = "messageId")
    @JsonIgnoreProperties("messages")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "projectId")
    @JsonIgnoreProperties("messages")
    private Project project;
}
