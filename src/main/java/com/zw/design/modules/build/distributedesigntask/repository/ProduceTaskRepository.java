package com.zw.design.modules.build.distributedesigntask.repository;

import com.zw.design.modules.build.distributedesigntask.entity.ProduceTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProduceTaskRepository extends JpaRepository<ProduceTask, Integer>, JpaSpecificationExecutor<ProduceTask> {

}
