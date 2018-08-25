package com.zw.design.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeptTaskDto implements Serializable {

    private Integer rowId;
    private Integer id;
    private Integer orderNo;
    private String name;
    private String code;
    private Integer num;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date planTime;
    private Integer type;
    private Integer deptNum;
    private Integer firstStep;
    private Integer secondStep;
    private Integer thirdStep;
    private Integer fourthStep;
    private Integer sumStatus;

}
