package com.zw.design.modules.overview.statistical.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class StatisticalModel implements Serializable {

    private Integer totalCount;
    private Integer finishedCount;
    private Integer unFinishedCount;
    private Integer cancelCount;
    private String groupCount;

}
