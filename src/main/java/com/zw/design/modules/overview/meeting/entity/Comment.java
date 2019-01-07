package com.zw.design.modules.overview.meeting.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zw.design.modules.overview.meeting.entity.Meeting;
import com.zw.design.modules.system.user.entity.SysUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date = new Date();

    private String content;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"roles"})
    private SysUser user;

    @ManyToOne
    @JoinColumn(name = "meetingId")
    @JsonIgnoreProperties("comments")
    private Meeting meeting;

}
