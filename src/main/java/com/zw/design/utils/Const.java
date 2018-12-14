package com.zw.design.utils;

import java.util.HashMap;
import java.util.Map;

public interface Const {

    String[] MACHINE_STEP_NAME = {"机械设计","提动作条件","报采购计划","下图纸"};
    String[] HYPRE_STEP_NAME = {"液压设计","提委托书","报采购计划","下图纸"};
    String[] ELECTRIC_STEP_NAME = {"电气设计","确定最终条件","报采购计划","下图纸"};
    String[] SOFTWARE_STEP_NAME = {"软件设计","编码及测试","报采购计划","试运行"};
    String[] PRODUCE_TASK_STEP_NAME = {"任务单","技术协议","合同","原材料外协计划","工艺编制","外购计划","铆焊制作","机加外协装配","基础条件","现场安装","调试运行","验收","图纸存档"};

    String[] DEPARTMENT_NAME = {"","机械设计","液压设计","电气设计","软件设计"};
    String[] DEPARTMENT_NUM = {"","一科","二科","三科","四科","五科","六科","七科","八科"};

    Map<String, String> FIELD_NAME = new HashMap<String, String>() {{
        put("name", "名称");
        put("code", "编号");
        put("date", "时间");
        put("roleName", "角色名称");
//        put("roleCode", "角色编码");
        put("deptName", "部门名称");
        put("title", "标题");
        put("content", "内容");
        put("address", "地址");
        put("personnel", "人员");
        put("orderNo", "排序");
    }};

}
