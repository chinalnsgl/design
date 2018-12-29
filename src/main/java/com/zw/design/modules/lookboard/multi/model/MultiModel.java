package com.zw.design.modules.lookboard.multi.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MultiModel implements Serializable {

    private Integer id;
    private String name;                            // 项目名称
    private String demander;                        // 需方
    private String address;                         // 安装地点
    private String code;                            // 项目号
    private String codeSpecial;                     // 任务单号
    private Integer num;                            // 数量
    private String designDepts;                     // 设计科室
    private Integer signStatus;                     // 技术协议
    private Integer contractStatus;                 // 合同
    private Integer sectionTaskStatus;               // 设计
    private Integer processTaskStatus;              // 工艺
    private Integer produceTaskStatus;              // 生产
    private Integer debugStatus;                    // 调试运行
    private Integer acceptStatus;                   // 验收
    private Integer saveStatus;                     // 图纸存档
    private String comment;                         // 备注
    private Integer projectStatus;                  // 项目状态
    private Integer messageCount;                   // 消息数量
}
