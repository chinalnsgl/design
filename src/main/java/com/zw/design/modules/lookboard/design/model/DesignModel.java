package com.zw.design.modules.lookboard.design.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class DesignModel implements Serializable {

    private Integer id;
    private String name;                            // 项目名称
    private String demander;                        // 需方
    private String address;                         // 安装地点
    private String code;                            // 项目号
    private String codeSpecial;                     // 任务单号
    private Integer num;                            // 数量
    private String sectionName;                     // 设计科室
    private Integer signStatus;                     // 技术协议
    private Integer firstStatus;                    // 设计
    private Integer secondStatus;                   // 条件相关
    private Integer thirdStatus;                    // 采购计划
    private Integer fourthStatus;                   // 下图纸
    private Integer debugStatus;                    // 调试运行
    private String comment;                         // 备注
    private Integer projectStatus;                  // 项目状态
    private Integer rowNumber;                      // 行号
}
