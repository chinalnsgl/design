package com.zw.design.modules.lookboard.multi.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.lookboard.multi.model.MultiModel;
import com.zw.design.modules.lookboard.multi.query.MultiQuery;

public interface MultiService {

    // 按条件查询项目表格模型数据
    BaseDataTableModel<MultiModel> findByQuery(MultiQuery query);

}
