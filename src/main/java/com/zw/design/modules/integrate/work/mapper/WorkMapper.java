package com.zw.design.modules.integrate.work.mapper;

import com.zw.design.modules.integrate.work.model.WorkModel;
import com.zw.design.modules.integrate.work.query.WorkQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkMapper {

    Integer findWorkModelCountByQuery(WorkQuery query);

    List<WorkModel> findWorkModelByQuery(WorkQuery query);

}
