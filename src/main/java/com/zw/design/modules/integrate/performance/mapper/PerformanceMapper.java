package com.zw.design.modules.integrate.performance.mapper;

import com.zw.design.modules.integrate.performance.model.PerformanceModel;
import com.zw.design.modules.integrate.performance.query.PerformanceQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceMapper {

    Integer findPerformanceCountByQuery(PerformanceQuery query);

    List<PerformanceModel> findPerformanceByQuery(PerformanceQuery query);
}
