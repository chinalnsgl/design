package com.zw.design.modules.system.sectiontype.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.log.service.LogService;
import com.zw.design.modules.system.sectiontype.entity.SectionType;
import com.zw.design.modules.system.sectiontype.query.SectionTypeQuery;
import com.zw.design.modules.system.sectiontype.repository.SectionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SectionTypeServiceImpl implements SectionTypeService {

    @Autowired
    private SectionTypeRepository sectionTypeRepository;
    @Autowired
    private LogService logService;

    // 按条件查询部门类型表格模型数据
    @Override
    public BaseDataTableModel<SectionType> findByQuery(SectionTypeQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength());
        Page<SectionType> secTypePage = sectionTypeRepository.findAll((Specification<SectionType>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (null != query.getNameQuery() && !"".equals(query.getNameQuery())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getNameQuery() + "%"));
            }
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        BaseDataTableModel<SectionType> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(secTypePage.getContent());
        baseDataTableModel.setRecordsTotal((int)secTypePage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)secTypePage.getTotalElements());
        return baseDataTableModel;
    }

    // 按名称查询部门类型
    @Override
    public SectionType findByName(String name) {
        return sectionTypeRepository.findByName(name);
    }

    // 按部门类型状态查询部门类型集合
    @Override
    public List<SectionType> findByStatus(Integer status) {
        return sectionTypeRepository.findByStatus(status);
    }

    // 保存部门类型
    @Override
    public SectionType saveSectionType(SectionType sectionType) {
        logService.saveLog("创建部门类型", sectionType.getName());
        return sectionTypeRepository.save(sectionType);
    }

    // 修改部门类型
    @Override
    public SectionType updateSectionType(SectionType sectionType) {
        SectionType sectionType1 = sectionTypeRepository.findById(sectionType.getId()).get();
        logService.saveLog("修改部门类型", sectionType1, sectionType);
        sectionType1.setName(sectionType.getName());
        return sectionTypeRepository.save(sectionType1);
    }

    // 修改部门类型状态
    @Override
    public SectionType updateSectionTypeStatus(Integer id, Integer status) {
        SectionType sectionType = sectionTypeRepository.findById(id).get();
        logService.saveLog("删除部门类型", sectionType.getName());
        sectionType.setStatus(status);
        return sectionTypeRepository.save(sectionType);
    }
}
