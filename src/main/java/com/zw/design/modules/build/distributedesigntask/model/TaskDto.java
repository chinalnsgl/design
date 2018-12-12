package com.zw.design.modules.build.distributedesigntask.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TaskDto implements Serializable {

    private Integer id;
    private Integer projectNo; // 序号
    private String code;
    private String codeSpecial;
    private String name;
    private String demander;
    private String address;
    private Integer num;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date planTime;
    private String comment;
    private Integer projectStatus;
    private Date createTime;
    private Date deliveryDate;
    private Integer deptType;
    private Integer deptNum;
    private Integer deptStatus;
    private String annotates;
    private String sendName;
    private Integer sendStatus;
    private String sendAnnotate;
    private String signName;
    private Integer signStatus;
    private String signAnnotate;
    private String produceName;
    private Integer produceStatus;
    private String produceAnnotate;
    private String installName;
    private Integer installStatus;
    private String installAnnotate;
    private String debugName;
    private Integer debugStatus;
    private String debugAnnotate;
    private String acceptName;
    private Integer acceptStatus;
    private String acceptAnnotate;
    private String saveName;
    private Integer saveStatus;
    private String saveAnnotate;
    private String contractName;
    private Integer contractStatus;
    private String contractAnnotate;
    private String contractNo;
    private Integer message;
}
