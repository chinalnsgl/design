package com.zw.design.modules.baseinfosetting.section.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.baseinfosetting.section.entity.Section;
import com.zw.design.modules.baseinfosetting.section.query.SectionQuery;

public interface SectionService {

    // 按条件查询部门表格模型数据
    BaseDataTableModel<Section> findByQuery(SectionQuery query);

    // 按名称查询部门
    Section findByNameAndStatus(String name, Integer status);

    // 保存部门
    Section saveSection(Section sectionType);

    // 修改部门
    Section updateSection(Section sectionType);

    // 修改部门状态
    Section updateSectionStatus(Integer id, Integer status);
}
