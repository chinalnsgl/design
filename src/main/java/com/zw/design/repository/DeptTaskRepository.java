package com.zw.design.repository;

import com.zw.design.entity.DeptTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeptTaskRepository extends JpaRepository<DeptTask, Integer>, JpaSpecificationExecutor<DeptTask> {

}
