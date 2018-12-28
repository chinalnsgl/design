package com.zw.design.modules.integrate.performance.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.baseinfosetting.section.entity.Section;
import com.zw.design.modules.baseinfosetting.section.repository.SectionRepository;
import com.zw.design.modules.integrate.performance.mapper.PerformanceMapper;
import com.zw.design.modules.integrate.performance.model.PerformanceModel;
import com.zw.design.modules.integrate.performance.query.PerformanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    @Autowired
    private PerformanceMapper performanceMapper;
    @Autowired
    private SectionRepository sectionRepository;

    // 按条件查询项目
    @Override
    public BaseDataTableModel<PerformanceModel> findProjectByQuery(PerformanceQuery query) {
        if (query.getNameQuery() != null && "".equals(query.getNameQuery().trim())) {
            query.setNameQuery("%" + query.getNameQuery().trim() + "%");
        }
        query.setLength(query.getStart() + query.getLength());
        query.setStart(query.getStart() + 1);
        Integer count = performanceMapper.findPerformanceCountByQuery(query);
        List<PerformanceModel> model = performanceMapper.findPerformanceByQuery(query);
        BaseDataTableModel<PerformanceModel> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(model);
        baseDataTableModel.setRecordsTotal(count);
        baseDataTableModel.setRecordsFiltered(count);
        return baseDataTableModel;
    }

    @Override
    public List<Section> findSectionByStatus(Integer status) {
        return sectionRepository.findByStatus(status);
    }
}
