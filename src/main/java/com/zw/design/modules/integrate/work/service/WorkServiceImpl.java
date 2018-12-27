package com.zw.design.modules.integrate.work.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.baseinfosetting.section.entity.Section;
import com.zw.design.modules.baseinfosetting.section.repository.SectionRepository;
import com.zw.design.modules.baseinfosetting.tasktype.entity.TaskType;
import com.zw.design.modules.baseinfosetting.tasktype.repository.TaskTypeRepository;
import com.zw.design.modules.integrate.work.mapper.WorkMapper;
import com.zw.design.modules.integrate.work.model.WorkModel;
import com.zw.design.modules.integrate.work.query.WorkQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private SectionRepository sectionRepository;

    @Override
    public List<TaskType> findTaskTypeByStatus(Integer status) {
        return taskTypeRepository.findByStatus(1);
    }

    @Override
    public List<Section> findSectionByStatus(Integer status) {
        return sectionRepository.findByStatus(1);
    }

    // 按条件查询项目工时模型
    @Override
    public BaseDataTableModel<WorkModel> findEmployeeByQuery(WorkQuery query) {
        if (query.getCodeQuery() != null && !"".equals(query.getCodeQuery().trim())) {
            query.setCodeQuery("%" + query.getCodeQuery().trim() + "%");
        }
        if (query.getNameQuery() != null && !"".equals(query.getNameQuery().trim())) {
            query.setNameQuery("%" + query.getNameQuery().trim() + "%");
        }
        if (query.getSectionQuery() != null && !"".equals(query.getSectionQuery().trim())) {
            query.setSectionQuery("%" + query.getSectionQuery().trim() + "%");
        }
        query.setLength(query.getStart() + query.getLength());
        query.setStart(query.getStart() + 1);
        Integer count = workMapper.findWorkModelCountByQuery(query);
        List<WorkModel> model = workMapper.findWorkModelByQuery(query);
        BaseDataTableModel<WorkModel> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(model);
        baseDataTableModel.setRecordsTotal(count);
        baseDataTableModel.setRecordsFiltered(count);
        return baseDataTableModel;
    }

}
