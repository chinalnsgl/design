package com.zw.design.modules.lookboard.design.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.lookboard.design.model.DesignModel;
import com.zw.design.modules.lookboard.design.query.DesignQuery;

public interface DesignService {

    // 按条件查询项目表格模型数据
    BaseDataTableModel<DesignModel> findByQuery(DesignQuery query);

}
