package com.zw.design.modules.integrate.status.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StatusQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private Integer statusQuery; // 状态查询
    private Integer taskQuery; // 任务查询

    public int getPageNum() {
        return start / length + 1;
    }
}
