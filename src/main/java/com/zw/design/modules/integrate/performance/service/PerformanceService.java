package com.zw.design.modules.integrate.performance.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.baseinfosetting.section.entity.Section;
import com.zw.design.modules.integrate.performance.model.PerformanceModel;
import com.zw.design.modules.integrate.performance.query.PerformanceQuery;

import java.util.List;

public interface PerformanceService {

    // 按条件查询项目
    BaseDataTableModel<PerformanceModel> findProjectByQuery(PerformanceQuery query);

    // 按状态查询科室
    List<Section> findSectionByStatus(Integer status);
}
