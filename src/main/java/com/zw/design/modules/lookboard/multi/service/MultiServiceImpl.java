package com.zw.design.modules.lookboard.multi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.lookboard.multi.mapper.MultiMapper;
import com.zw.design.modules.lookboard.multi.model.MultiModel;
import com.zw.design.modules.lookboard.multi.query.MultiQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultiServiceImpl implements MultiService {

    @Autowired
    private MultiMapper multiMapper;


    // 按条件查询项目表格模型数据
    @Override
    public BaseDataTableModel<MultiModel> findByQuery(MultiQuery query) {
        if (query.getCodeQuery() != null && !"".equals(query.getCodeQuery().trim())) {
            query.setCodeQuery("%" + query.getCodeQuery() + "%");
        }
        if (query.getNameQuery() != null && !"".equals(query.getNameQuery().trim())) {
            query.setNameQuery("%" + query.getNameQuery() + "%");
        }
        if (query.getDemanderQuery() != null && !"".equals(query.getDemanderQuery().trim())) {
            query.setDemanderQuery("%" + query.getDemanderQuery() + "%");
        }
        if (query.getSectionQuery() != null && !"".equals(query.getSectionQuery().trim())) {
            query.setSectionQuery("%" + query.getSectionQuery() + "%");
        }
        PageHelper.startPage(query.getPageNum(), query.getLength());
        List<MultiModel> model = multiMapper.findProjectByQuery(query);
        PageInfo<MultiModel> pageInfo = new PageInfo(model);
        BaseDataTableModel<MultiModel> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(model);
        baseDataTableModel.setRecordsTotal((int)pageInfo.getTotal());
        baseDataTableModel.setRecordsFiltered((int)pageInfo.getTotal());
        return baseDataTableModel;
    }

}
