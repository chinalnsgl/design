package com.zw.design.modules.system.log.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.log.entity.LogInfo;
import com.zw.design.modules.system.log.query.LogQuery;

public interface LogService {

    LogInfo saveLog(LogInfo logInfo);

    BaseDataTableModel<LogInfo> findLogByCriteria(LogQuery query);

    LogInfo saveLog(String operationName, Object before, Object after);

    LogInfo saveLog(String operationName, String operationContent);
}
