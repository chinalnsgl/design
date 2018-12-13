package com.zw.design.modules.lookboard.process.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.lookboard.process.model.ProcessModel;
import com.zw.design.modules.lookboard.process.query.ProcessQuery;

public interface ProcessService {

    // 按条件查询项目表格模型数据
    BaseDataTableModel<ProcessModel> findByQuery(ProcessQuery query);

}
