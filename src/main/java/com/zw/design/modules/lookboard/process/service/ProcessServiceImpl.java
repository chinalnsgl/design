package com.zw.design.modules.lookboard.process.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.lookboard.process.mapper.ProcessMapper;
import com.zw.design.modules.lookboard.process.model.ProcessModel;
import com.zw.design.modules.lookboard.process.query.ProcessQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessMapper processMapper;


    // 按条件查询项目表格模型数据
    @Override
    public BaseDataTableModel<ProcessModel> findByQuery(ProcessQuery query) {
        if (query.getCodeQuery() != null && !"".equals(query.getCodeQuery().trim())) {
            query.setCodeQuery("%" + query.getCodeQuery() + "%");
        }
        if (query.getNameQuery() != null && !"".equals(query.getNameQuery().trim())) {
            query.setNameQuery("%" + query.getNameQuery() + "%");
        }
        if (query.getDemanderQuery() != null && !"".equals(query.getDemanderQuery().trim())) {
            query.setDemanderQuery("%" + query.getDemanderQuery().trim() + "%");
        }
        if (query.getAddressQuery() != null && !"".equals(query.getAddressQuery().trim())) {
            query.setAddressQuery("%" + query.getAddressQuery().trim() + "%");
        }
        PageHelper.startPage(query.getPageNum(), query.getLength());
        List<ProcessModel> model = processMapper.findProjectByQuery(query);
        PageInfo<ProcessModel> pageInfo = new PageInfo(model);
        BaseDataTableModel<ProcessModel> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(model);
        baseDataTableModel.setRecordsTotal((int)pageInfo.getTotal());
        baseDataTableModel.setRecordsFiltered((int)pageInfo.getTotal());
        return baseDataTableModel;
    }

}
