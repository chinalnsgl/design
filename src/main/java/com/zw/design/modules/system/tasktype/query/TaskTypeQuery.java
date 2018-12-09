package com.zw.design.modules.system.tasktype.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskTypeQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String nameQuery; // 部门类型名称
}
