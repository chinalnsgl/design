package com.zw.design.modules.build.distributedesigntask.repository;

import com.zw.design.modules.build.distributedesigntask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

}
