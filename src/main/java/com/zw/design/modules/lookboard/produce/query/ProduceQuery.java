package com.zw.design.modules.lookboard.produce.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProduceQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String codeQuery; // 项目号
    private String nameQuery; // 项目名
    private Integer statusQuery; // 状态
    private Integer yearQuery; // 年份
    private Date startTimeQuery;
    private Date endTimeQuery;
    private String demanderQuery;
    private String addressQuery;
    private Integer numQuery;

    public int getPageNum() {
        return start / length + 1;
    }
}
