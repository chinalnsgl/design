package com.zw.design.query;

import lombok.Data;

import java.util.Date;

@Data
public class LogQuery extends BaseQuery {

    private String name; // 用户名
    private Date startTime;
    private Date endTime;
}
