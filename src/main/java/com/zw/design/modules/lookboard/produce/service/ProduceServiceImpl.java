package com.zw.design.modules.lookboard.produce.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.lookboard.produce.mapper.ProduceMapper;
import com.zw.design.modules.lookboard.produce.model.ProduceModel;
import com.zw.design.modules.lookboard.produce.query.ProduceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduceServiceImpl implements ProduceService {

    @Autowired
    private ProduceMapper produceMapper;


    // 按条件查询项目表格模型数据
    @Override
    public BaseDataTableModel<ProduceModel> findByQuery(ProduceQuery query) {
        if (query.getCodeQuery() != null && !"".equals(query.getCodeQuery().trim())) {
            query.setCodeQuery("%" + query.getCodeQuery() + "%");
        }
        if (query.getNameQuery() != null && !"".equals(query.getNameQuery().trim())) {
            query.setNameQuery("%" + query.getNameQuery() + "%");
        }
        PageHelper.startPage(query.getPageNum(), query.getLength());
        List<ProduceModel> model = produceMapper.findProjectByQuery(query);
        PageInfo<ProduceModel> pageInfo = new PageInfo(model);
        BaseDataTableModel<ProduceModel> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(model);
        baseDataTableModel.setRecordsTotal((int)pageInfo.getTotal());
        baseDataTableModel.setRecordsFiltered((int)pageInfo.getTotal());
        return baseDataTableModel;
    }

}
