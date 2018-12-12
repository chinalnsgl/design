package com.zw.design.modules.build.distributedesigntask.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeptTaskDto implements Serializable {

    private Integer rowId;
    private Integer id;
    private Integer orderNo;
    private Integer projectNo;
    private String name;
    private String code;
    private Integer num;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date planTime;
    private String comment;
    private String demander;
    private String address;
    private String codeSpecial;
    private Integer projectStatus;
    private Integer type;
    private Integer deptNum;
    private Integer firstStep;
    private Integer secondStep;
    private Integer thirdStep;
    private Integer fourthStep;
    private Integer sumStatus;
    private Integer signStatus;
    private Integer debugStatus;

    private Float produceStatus;
    private Float processStatus;
    private Float deptStatus;

}
