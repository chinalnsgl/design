package com.zw.design.modules.system.projecttype.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectTypeQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String nameQuery; // 员工名称
}
