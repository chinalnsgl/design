package com.zw.design.modules.integrate.performance.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PerformanceQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String nameQuery;       // 负责人
    private String sectionQuery;    // 科室
    private Date startTimeQuery;
    private Date endTimeQuery;

    public int getPageNum() {
        return start / length + 1;
    }
}
