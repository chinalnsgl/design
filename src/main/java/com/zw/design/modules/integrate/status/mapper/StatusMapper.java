package com.zw.design.modules.integrate.status.mapper;

import com.zw.design.modules.integrate.status.model.StatusModel;
import com.zw.design.modules.integrate.status.query.StatusQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusMapper {

    List<StatusModel> findProjectByQuery(StatusQuery query);
}
