package com.zw.design.modules.build.create.repository;

import com.zw.design.modules.build.create.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {

    Project findByCode(String code);

    Project findByCodeAndStatus(String code, Integer status);
}
