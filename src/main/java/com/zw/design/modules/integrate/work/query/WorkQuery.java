package com.zw.design.modules.integrate.work.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class WorkQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String codeQuery;     // 项目号

    public int getPageNum() {
        return start / length + 1;
    }
}
