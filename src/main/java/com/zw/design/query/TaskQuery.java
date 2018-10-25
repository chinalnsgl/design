package com.zw.design.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TaskQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private Integer type; // 分类
    private Integer num; // 科室编号
    private Integer status; // 状态
    private String code;
    private String name;
    private String departmentQuery;
    private Date startTime;
    private Date endTime;
    private String ids;
    private Integer firstStatus;
    private Integer secondStatus;
    private Integer thirdStatus;
    private Integer year;

}
