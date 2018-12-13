package com.zw.design.modules.lookboard.produce.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProduceQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String codeQuery; // 项目号
    private String nameQuery; // 项目名
    private Integer statusQuery;
    private Integer yearQuery;

    public int getPageNum() {
        return start / length + 1;
    }
}
