package com.zw.design.modules.lookboard.process.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProcessModel implements Serializable {

    private Integer id;
    private String name;                            // 项目名称
    private String demander;                        // 需方
    private String address;                         // 安装地点
    private String code;                            // 项目号
    private String codeSpecial;                     // 任务单号
    private Integer num;                            // 数量
    private Integer firstStatus;                        // 原材料外协计划状态
    private Integer secondStatus;                        // 工艺编制状态
    private Integer thirdStatus;                        // 外购计划状态
    private String comment;                         // 备注
    private Integer projectStatus;                  // 项目状态
}
