package com.zw.design.modules.baseinfosetting.sectiontype.repository;

import com.zw.design.modules.baseinfosetting.sectiontype.entity.SectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SectionTypeRepository extends JpaRepository<SectionType, Integer>, JpaSpecificationExecutor<SectionType> {

    SectionType findByNameAndStatus(String name, Integer status);

    List<SectionType> findByStatus(Integer status);
}
