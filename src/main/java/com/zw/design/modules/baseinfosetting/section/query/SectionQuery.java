package com.zw.design.modules.baseinfosetting.section.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class SectionQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String nameQuery; // 部门类型名称
}
