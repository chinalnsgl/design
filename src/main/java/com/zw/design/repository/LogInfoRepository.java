package com.zw.design.repository;

import com.zw.design.entity.LogInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogInfoRepository extends JpaRepository<LogInfo, Integer>, JpaSpecificationExecutor<LogInfo> {

}
