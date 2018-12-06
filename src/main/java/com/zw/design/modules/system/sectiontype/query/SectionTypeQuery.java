package com.zw.design.modules.system.sectiontype.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class SectionTypeQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String name; // 部门类型名称
}
