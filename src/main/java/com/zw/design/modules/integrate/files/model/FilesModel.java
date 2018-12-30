package com.zw.design.modules.integrate.files.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilesModel implements Serializable {

    private Integer id;
    private String name;                            // 项目名称
    private String demander;                        // 需方
    private String address;                         // 安装地点
    private String code;                            // 项目号
    private String codeSpecial;                     // 任务单号
    private Integer num;                            // 数量
    private Integer taskSheetCount;                        // 任务单数量
    private Integer protocolCount;                        // 技术协议数量
    private Integer contractCount;                        // 合同数量
    private Integer acceptCount;                  // 验收数量
}
