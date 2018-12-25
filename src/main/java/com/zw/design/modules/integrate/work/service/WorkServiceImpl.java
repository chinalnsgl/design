package com.zw.design.modules.integrate.work.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.integrate.work.query.WorkQuery;
import com.zw.design.modules.lookboard.single.entity.TaskEmployee;
import com.zw.design.modules.lookboard.single.repository.TaskEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private TaskEmployeeRepository taskEmployeeRepository;

    // 按条件查询项目
    @Override
    public BaseDataTableModel<TaskEmployee> findEmployeeByQuery(WorkQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(), Sort.by(Sort.Direction.DESC,"id"));
        Page<TaskEmployee> taskEmployeePage = taskEmployeeRepository.findAll((Specification<TaskEmployee>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            list.add(criteriaBuilder.equal(root.get("status"), 1));
            list.add(criteriaBuilder.equal(root.get("task").get("project").get("code"), query.getCodeQuery().trim()));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        BaseDataTableModel<TaskEmployee> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(taskEmployeePage.getContent());
        baseDataTableModel.setRecordsTotal((int)taskEmployeePage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)taskEmployeePage.getTotalElements());
        return baseDataTableModel;
    }

}
