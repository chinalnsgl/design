package com.zw.design.modules.overview.meeting.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MeetingQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String titleQuery; // 主题
    private Date startTimeQuery;
    private Date endTimeQuery;
}
