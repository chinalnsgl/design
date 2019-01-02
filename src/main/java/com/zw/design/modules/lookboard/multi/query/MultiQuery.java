package com.zw.design.modules.lookboard.multi.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MultiQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String codeQuery; // 项目号
    private String nameQuery; // 项目名
    private Integer statusQuery;
    private Date startTimeQuery;
    private Date endTimeQuery;
    private String sectionQuery;
    private String demanderQuery;
    private Integer yearQuery;
    private String addressQuery;
    private Integer numQuery;

    public int getPageNum() {
        return start / length + 1;
    }
}
