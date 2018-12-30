package com.zw.design.modules.integrate.files.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FilesQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String codeQuery; // 项目号
    private Integer yearQuery; // 年份
    private Integer statusQuery; // 状态
    private Integer taskSheetQuery; // 任务单
    private Integer protocolQuery; // 技术协议
    private Integer contractQuery; // 合同
    private Integer acceptQuery; // 验收

    public int getPageNum() {
        return start / length + 1;
    }
}
