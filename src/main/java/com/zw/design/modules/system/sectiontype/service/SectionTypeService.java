package com.zw.design.modules.system.sectiontype.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.sectiontype.entity.SectionType;
import com.zw.design.modules.system.sectiontype.query.SectionTypeQuery;

import java.util.List;

public interface SectionTypeService {

    // 按条件查询部门类型表格模型数据
    BaseDataTableModel<SectionType> findByQuery(SectionTypeQuery query);

    // 按名称查询部门类型
    SectionType findByName(String name);

    // 按部门类型状态查询部门类型集合
    List<SectionType> findByStatus(Integer status);

    // 保存部门类型
    SectionType saveSectionType(SectionType sectionType);

    // 修改部门类型
    SectionType updateSectionType(SectionType sectionType);

    // 修改部门类型状态
    SectionType updateSectionTypeStatus(Integer id, Integer status);
}
