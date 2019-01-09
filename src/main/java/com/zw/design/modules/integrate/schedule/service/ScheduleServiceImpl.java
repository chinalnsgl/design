package com.zw.design.modules.integrate.schedule.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.integrate.schedule.mapper.ScheduleMapper;
import com.zw.design.modules.integrate.schedule.model.ScheduleModel;
import com.zw.design.modules.integrate.schedule.query.ScheduleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    // 按条件查询项目
    @Override
    public BaseDataTableModel<ScheduleModel> findProjectByQuery(ScheduleQuery query) {
        if (query.getCodeQuery() != null && !"".equals(query.getCodeQuery().trim())) {
            query.setCodeQuery("%" + query.getCodeQuery().trim() + "%");
        }
        if (query.getNameQuery() != null && !"".equals(query.getNameQuery().trim())) {
            query.setNameQuery("%" + query.getNameQuery().trim() + "%");
        }
        PageHelper.startPage(query.getPageNum(), query.getLength());
        List<ScheduleModel> model = scheduleMapper.findProjectByQuery(query);
        PageInfo<ScheduleModel> pageInfo = new PageInfo(model);
        BaseDataTableModel<ScheduleModel> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(model);
        baseDataTableModel.setRecordsTotal((int)pageInfo.getTotal());
        baseDataTableModel.setRecordsFiltered((int)pageInfo.getTotal());
        return baseDataTableModel;
    }

}
