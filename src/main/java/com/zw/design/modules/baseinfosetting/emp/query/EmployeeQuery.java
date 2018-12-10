package com.zw.design.modules.baseinfosetting.emp.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String nameQuery; // 员工名称
    private String codeQuery; // 员工编号
    private Integer deptId; // 部门
}
