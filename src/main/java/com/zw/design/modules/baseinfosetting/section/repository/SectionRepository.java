package com.zw.design.modules.baseinfosetting.section.repository;

import com.zw.design.modules.baseinfosetting.section.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Integer>, JpaSpecificationExecutor<Section> {

    Section findByNameAndStatus(String name, Integer Status);

    List<Section> findByStatus(Integer status);
}
