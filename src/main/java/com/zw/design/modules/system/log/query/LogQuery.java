package com.zw.design.modules.system.log.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LogQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String name; // 用户名
    private Date startTime;
    private Date endTime;
}
