package com.zw.design.modules.system.projecttype.repository;

import com.zw.design.modules.system.projecttype.entity.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectTypeRepository extends JpaRepository<ProjectType, Integer>, JpaSpecificationExecutor<ProjectType> {

    List<ProjectType> findByStatus(Integer status);

    ProjectType findByName(String name);
}
