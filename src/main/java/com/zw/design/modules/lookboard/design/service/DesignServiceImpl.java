package com.zw.design.modules.lookboard.design.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.lookboard.design.mapper.DesignMapper;
import com.zw.design.modules.lookboard.design.model.DesignModel;
import com.zw.design.modules.lookboard.design.query.DesignQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignServiceImpl implements DesignService {

    @Autowired
    private DesignMapper designMapper;


    // 按条件查询项目表格模型数据
    @Override
    public BaseDataTableModel<DesignModel> findByQuery(DesignQuery query) {
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
        List<Integer> ids = designMapper.findProjectIdsBySection(query);
        // 没有符合要求的项目带入一个查询不到的条件，否则会查询全部记录
        if (ids.size() < 1) {
            ids.add(-1);
        }
        query.setIds(StringUtils.join(ids.toArray(), ","));
        PageHelper.startPage(query.getPageNum(), query.getLength());
        List<DesignModel> model = designMapper.findProjectByQuery(query);
        PageInfo<DesignModel> pageInfo = new PageInfo(model);
        BaseDataTableModel<DesignModel> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(model);
        baseDataTableModel.setRecordsTotal((int)pageInfo.getTotal());
        baseDataTableModel.setRecordsFiltered((int)pageInfo.getTotal());
        return baseDataTableModel;
    }

}
