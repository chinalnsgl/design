package com.zw.design.modules.system.log.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class LogInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date operationTime; // 操作时间

    private String operationName; // 操作名称

    @Column(columnDefinition = "text")
    private String operationContent;

    private String address; // ip

    private String account; // 帐号

    private String name; // 名称

}
