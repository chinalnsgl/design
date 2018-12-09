package com.zw.design.modules.system.section.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.log.service.LogService;
import com.zw.design.modules.system.section.entity.Section;
import com.zw.design.modules.system.section.query.SectionQuery;
import com.zw.design.modules.system.section.repository.SectionRepository;
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
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private LogService logService;

    // 按条件查询部门表格模型数据
    @Override
    public BaseDataTableModel<Section> findByQuery(SectionQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength());
        Page<Section> sectionPage = sectionRepository.findAll((Specification<Section>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (null != query.getNameQuery() && !"".equals(query.getNameQuery())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getNameQuery() + "%"));
            }
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        BaseDataTableModel<Section> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(sectionPage.getContent());
        baseDataTableModel.setRecordsTotal((int)sectionPage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)sectionPage.getTotalElements());
        return baseDataTableModel;
    }

    // 按名称查询部门
    @Override
    public Section findByNameAndStatus(String name, Integer status) {
        return sectionRepository.findByNameAndStatus(name, status);
    }

    // 保存部门
    @Override
    public Section saveSection(Section sectionType) {
        logService.saveLog("创建部门", sectionType.getName());
        return sectionRepository.save(sectionType);
    }

    // 修改部门
    @Override
    public Section updateSection(Section sec) {
        Section section = sectionRepository.findById(sec.getId()).get();
        logService.saveLog("修改部门", section, sec);
        section.setName(sec.getName());
        return sectionRepository.save(section);
    }

    // 修改部门状态
    @Override
    public Section updateSectionStatus(Integer id, Integer status) {
        Section section = sectionRepository.findById(id).get();
        logService.saveLog("删除部门", section.getName());
        section.setStatus(status);
        return sectionRepository.save(section);
    }
}
