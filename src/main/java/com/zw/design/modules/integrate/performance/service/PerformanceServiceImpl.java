package com.zw.design.modules.integrate.performance.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.integrate.performance.mapper.PerformanceMapper;
import com.zw.design.modules.integrate.performance.query.PerformanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    @Autowired
    private PerformanceMapper performanceMapper;


    // 按条件查询项目
    @Override
    public BaseDataTableModel<Project> findProjectByQuery(PerformanceQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getLength());
        List<Project> model = performanceMapper.findProjectByQuery(query);
        PageInfo<Project> pageInfo = new PageInfo(model);
        BaseDataTableModel<Project> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(model);
        baseDataTableModel.setRecordsTotal((int)pageInfo.getTotal());
        baseDataTableModel.setRecordsFiltered((int)pageInfo.getTotal());
        return baseDataTableModel;
    }

}
