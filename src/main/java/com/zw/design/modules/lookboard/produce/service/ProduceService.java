package com.zw.design.modules.lookboard.produce.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.lookboard.produce.model.ProduceModel;
import com.zw.design.modules.lookboard.produce.query.ProduceQuery;

public interface ProduceService {

    // 按条件查询项目表格模型数据
    BaseDataTableModel<ProduceModel> findByQuery(ProduceQuery query);

}
