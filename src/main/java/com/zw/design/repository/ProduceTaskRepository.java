package com.zw.design.repository;

import com.zw.design.entity.ProduceTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProduceTaskRepository extends JpaRepository<ProduceTask, Integer>, JpaSpecificationExecutor<ProduceTask> {

}
