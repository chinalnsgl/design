package com.zw.design.modules.overview.meeting.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zw.design.modules.system.user.entity.SysUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Meeting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title; // 会议标题

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;// 会议时间

    private String address; // 会议地点

    private String personnel; // 参与人员

    @Column(columnDefinition = "text")
    private String content; // 会议内容

    private Integer status = 1; // 0: 删除   1：保存   2：提交

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime = new Date(); // 创建时间

    @OneToMany(mappedBy = "meeting")
    @JsonIgnoreProperties("meeting")
    List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "userId")
    private SysUser user; // 发布人

}
