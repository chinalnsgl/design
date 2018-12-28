package com.zw.design.modules.integrate.performance.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PerformanceModel implements Serializable {

    private String empName;                         // 负责人
    private String code;                            // 项目号
    private String name;                            // 项目名称
    private Integer duration;                       // 历时
    private String alias;                           // 任务名称

}
