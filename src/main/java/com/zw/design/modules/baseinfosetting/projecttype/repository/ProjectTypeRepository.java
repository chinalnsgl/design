package com.zw.design.modules.baseinfosetting.projecttype.repository;

import com.zw.design.modules.baseinfosetting.projecttype.entity.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectTypeRepository extends JpaRepository<ProjectType, Integer>, JpaSpecificationExecutor<ProjectType> {

    List<ProjectType> findByStatus(Integer status);

    ProjectType findByNameAndStatus(String name, Integer status);
}
