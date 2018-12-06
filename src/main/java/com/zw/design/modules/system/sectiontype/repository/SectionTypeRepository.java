package com.zw.design.modules.system.sectiontype.repository;

import com.zw.design.modules.system.sectiontype.entity.SectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SectionTypeRepository extends JpaRepository<SectionType, Integer>, JpaSpecificationExecutor<SectionType> {

    SectionType findByName(String name);

}
