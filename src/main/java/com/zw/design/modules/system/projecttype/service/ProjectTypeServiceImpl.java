package com.zw.design.modules.system.projecttype.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.log.service.LogService;
import com.zw.design.modules.system.projecttype.entity.ProjectType;
import com.zw.design.modules.system.projecttype.query.ProjectTypeQuery;
import com.zw.design.modules.system.projecttype.repository.ProjectTypeRepository;
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
public class ProjectTypeServiceImpl implements ProjectTypeService {

    @Autowired
    private ProjectTypeRepository projectTypeRepository;
    @Autowired
    private LogService logService;


    // 按条件查询所有项目类型表格模型数据
    @Override
    public BaseDataTableModel<ProjectType> findProjectTypeByQuery(ProjectTypeQuery query) {
        // 分页排序
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(), Sort.by(Sort.Direction.ASC,"id"));
        // 分页数据
        Page<ProjectType> empPage = projectTypeRepository.findAll((Specification<ProjectType>) (root, criteriaQuery, criteriaBuilder) -> {
            // 构建查询条件
            List<Predicate> list = new ArrayList<>();
            if (null != query.getNameQuery() && !"".equals(query.getNameQuery())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getNameQuery() + "%"));
            }
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        // 封装基础表格模型数据
        BaseDataTableModel<ProjectType> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(empPage.getContent());
        baseDataTableModel.setRecordsTotal((int)empPage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)empPage.getTotalElements());
        return baseDataTableModel;
    }

    // 按ID查询项目类型
    @Override
    public ProjectType findById(Integer id) {
        return projectTypeRepository.findById(id).get();
    }

    // 按名称查询项目类型
    @Override
    public ProjectType findByNameAndStatus(String name, Integer status) {
        return projectTypeRepository.findByNameAndStatus(name, status);
    }

    // 保存项目类型
    @Override
    public ProjectType saveProjectType(ProjectType projectType) {
        logService.saveLog("新建项目类型" , projectType.getName());
        return projectTypeRepository.save(projectType);
    }

    // 修改项目类型
    @Override
    public ProjectType updateProjectType(ProjectType projectType) {
        ProjectType pt = findById(projectType.getId());
        logService.saveLog("修改项目类型", pt, projectType);
        pt.setName(projectType.getName());
        return projectTypeRepository.save(pt);
    }

    // 修改项目类型状态
    @Override
    public ProjectType updateProjectTypeStatus(Integer id, Integer status) {
        ProjectType projectType = findById(id);
        logService.saveLog("删除项目类型" , projectType.getName());
        projectType.setStatus(status);
        return projectTypeRepository.save(projectType);
    }

}
