package com.zw.design.modules.integrate.performance.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class PerformanceQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String nameQuery;     // 发起者

    public int getPageNum() {
        return start / length + 1;
    }
}
