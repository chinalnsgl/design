package com.zw.design.modules.integrate.files.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.integrate.files.mapper.FilesMapper;
import com.zw.design.modules.integrate.files.model.FilesModel;
import com.zw.design.modules.integrate.files.query.FilesQuery;
import com.zw.design.modules.lookboard.process.model.ProcessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilesServiceImpl implements FilesService {

    @Autowired
    private FilesMapper filesMapper;


    // 按条件查询项目表格模型数据
    @Override
    public BaseDataTableModel<FilesModel> findByQuery(FilesQuery query) {
        if (query.getCodeQuery() != null && !"".equals(query.getCodeQuery().trim())) {
            query.setCodeQuery("%" + query.getCodeQuery() + "%");
        }
        PageHelper.startPage(query.getPageNum(), query.getLength());
        List<FilesModel> model = filesMapper.findProjectByQuery(query);
        PageInfo<FilesModel> pageInfo = new PageInfo(model);
        BaseDataTableModel<FilesModel> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(model);
        baseDataTableModel.setRecordsTotal((int)pageInfo.getTotal());
        baseDataTableModel.setRecordsFiltered((int)pageInfo.getTotal());
        return baseDataTableModel;
    }

}
