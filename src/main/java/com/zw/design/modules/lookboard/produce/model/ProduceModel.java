package com.zw.design.modules.lookboard.produce.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProduceModel implements Serializable {

    private Integer id;
    private String name;                            // 项目名称
    private String demander;                        // 需方
    private String address;                         // 安装地点
    private String code;                            // 项目号
    private String codeSpecial;                     // 任务单号
    private Integer num;                            // 数量
    private Integer firstStatus;                    // 铆焊制作
    private Integer secondStatus;                   // 机加外协装配
    private Integer thirdStatus;                    // 基础条件
    private Integer fourthStatus;                   // 现场安装
    private String comment;                         // 备注
    private Integer projectStatus;                  // 项目状态
}
