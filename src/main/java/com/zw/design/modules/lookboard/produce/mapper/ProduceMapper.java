package com.zw.design.modules.lookboard.produce.mapper;

import com.zw.design.modules.lookboard.produce.model.ProduceModel;
import com.zw.design.modules.lookboard.produce.query.ProduceQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduceMapper {

    List<ProduceModel> findProjectByQuery(ProduceQuery query);
}
