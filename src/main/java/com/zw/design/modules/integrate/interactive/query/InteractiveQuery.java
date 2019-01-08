package com.zw.design.modules.integrate.interactive.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InteractiveQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String senderQuery;     // 发起者
    private String receiverQuery;   // 接收者
    private Integer projectIdQuery; // 项目ID
    private Date startTimeQuery;
    private Date endTimeQuery;

    public int getPageNum() {
        return start / length + 1;
    }
}
