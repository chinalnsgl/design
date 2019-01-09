package com.zw.design.modules.integrate.schedule.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ScheduleModel implements Serializable {

    private Integer id;
    private String name;                            // 项目名称
    private String demander;                        // 需方
    private String address;                         // 安装地点
    private String code;                            // 项目号
    private Integer num;                            // 数量
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date projectCompleteTime;                    // 项目完成时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date sectionCompleteTime;                    // 设计完成时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date produceCompleteTime;                     // 生产完成时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date processCompleteTime;                     // 工艺完成时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date taskListCompleteTime;                     // 任务单完成时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date signCompleteTime;                     // 协议完成时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date contractCompleteTime;                     // 合同完成时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date debugCompleteTime;                     // 调试完成时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date acceptCompleteTime;                     // 验收完成时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date saveCompleteTime;                     // 存档完成时间
    private String comment;                         // 备注
}
