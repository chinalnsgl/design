package com.zw.design.modules.integrate.work.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WorkQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String codeQuery;     // 项目号
    private String nameQuery;     // 项目名
    private String sectionQuery;     // 科室名
    private String typeQuery;     // 任务类型
    private Date startTimeQuery;
    private Date endTimeQuery;


    public int getPageNum() {
        return start / length + 1;
    }
}
