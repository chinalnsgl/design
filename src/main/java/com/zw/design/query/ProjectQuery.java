package com.zw.design.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProjectQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String code; // 项目号
    private String name; // 项目名
    private Integer type; // 科室类型
    private Integer num; // 科室编号
    private Date startTime;
    private Date endTime;
    private String departmentQuery;
    private String demander;
    private Integer statusQuery;
    private Integer year;
}
