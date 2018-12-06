package com.zw.design.modules.build.distribute.repository;

import com.zw.design.modules.build.distribute.entity.DeptTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeptTaskRepository extends JpaRepository<DeptTask, Integer>, JpaSpecificationExecutor<DeptTask> {

}
