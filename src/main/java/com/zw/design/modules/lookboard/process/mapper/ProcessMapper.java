package com.zw.design.modules.lookboard.process.mapper;

import com.zw.design.modules.lookboard.process.model.ProcessModel;
import com.zw.design.modules.lookboard.process.query.ProcessQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessMapper {

    List<ProcessModel> findProjectByQuery(ProcessQuery query);
}
