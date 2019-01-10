package com.zw.design.modules.integrate.status.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.integrate.status.model.StatusModel;
import com.zw.design.modules.integrate.status.query.StatusQuery;

public interface StatusService {

    // 按条件查询项目表格模型数据
    BaseDataTableModel<StatusModel> findByQuery(StatusQuery query);

}
