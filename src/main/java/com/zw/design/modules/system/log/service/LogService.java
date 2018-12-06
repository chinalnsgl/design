package com.zw.design.modules.system.log.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.log.entity.LogInfo;
import com.zw.design.modules.system.log.query.LogQuery;

public interface LogService {

    //  按条件查询日志表格模型数据
    BaseDataTableModel<LogInfo> findLogByQuery(LogQuery query);

    // 保存日志
    LogInfo saveLog(LogInfo logInfo);

    // 保存日志
    LogInfo saveLog(String operationName, Object before, Object after);

    // 保存日志
    LogInfo saveLog(String operationName, String operationContent);
}
