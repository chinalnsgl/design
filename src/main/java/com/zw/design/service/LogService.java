package com.zw.design.service;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.LogInfo;
import com.zw.design.query.LogQuery;

import javax.servlet.http.HttpServletRequest;

public interface LogService {

    LogInfo saveLog(LogInfo logInfo);

    DataTablesCommonDto<LogInfo> findLogByCriteria(LogQuery query);

    LogInfo saveLog(String content, HttpServletRequest request);
}
