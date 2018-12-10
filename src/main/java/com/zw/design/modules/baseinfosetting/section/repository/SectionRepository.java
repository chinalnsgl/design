package com.zw.design.modules.baseinfosetting.section.repository;

import com.zw.design.modules.baseinfosetting.section.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SectionRepository extends JpaRepository<Section, Integer>, JpaSpecificationExecutor<Section> {

    Section findByNameAndStatus(String name, Integer Status);

}
