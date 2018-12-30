package com.zw.design.modules.integrate.files.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.integrate.files.model.FilesModel;
import com.zw.design.modules.integrate.files.query.FilesQuery;

public interface FilesService {

    // 按条件查询项目表格模型数据
    BaseDataTableModel<FilesModel> findByQuery(FilesQuery query);

}
