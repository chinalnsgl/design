package com.zw.design.modules.integrate.work.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class WorkModel implements Serializable {

    private String code;                            // 项目号
    private String name;                            // 项目名称
    private String empName;                         // 负责人
    private Integer duration;                       // 历时
    private String typeName;                        // 任务类型
    private String alias;                           // 任务名称

}
