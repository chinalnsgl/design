package com.zw.design.modules.integrate.status.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.integrate.status.mapper.StatusMapper;
import com.zw.design.modules.integrate.status.model.StatusModel;
import com.zw.design.modules.integrate.status.query.StatusQuery;
import com.zw.design.modules.lookboard.multi.model.MultiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusMapper statusMapper;

    // 按条件查询项目表格模型数据
    @Override
    public BaseDataTableModel<StatusModel> findByQuery(StatusQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getLength());
        List<StatusModel> model = statusMapper.findProjectByQuery(query);
        PageInfo<StatusModel> pageInfo = new PageInfo(model);
        BaseDataTableModel<StatusModel> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(model);
        baseDataTableModel.setRecordsTotal((int)pageInfo.getTotal());
        baseDataTableModel.setRecordsFiltered((int)pageInfo.getTotal());
        return baseDataTableModel;
    }

}
