package com.zw.design.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date = new Date();
    private String content;
    @ManyToOne
    @JoinColumn(name = "userId")
    private SysUser user;
    @ManyToOne
    @JoinColumn(name = "meetingId")
    @JsonIgnoreProperties("comments")
    private Meeting meeting;

}
