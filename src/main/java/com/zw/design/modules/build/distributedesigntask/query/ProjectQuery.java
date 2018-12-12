package com.zw.design.modules.build.distributedesigntask.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProjectQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String codeQuery; // 项目号
    private String nameQuery; // 项目名
    private Integer statusQuery;
    private Integer type; // 科室类型
    private Integer num; // 科室编号
    private Date startTime;
    private Date endTime;
    private String departmentQuery;
    private String demander;
    private Integer year;
}
