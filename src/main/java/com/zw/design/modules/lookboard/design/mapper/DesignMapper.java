package com.zw.design.modules.lookboard.design.mapper;

import com.zw.design.modules.lookboard.design.model.DesignModel;
import com.zw.design.modules.lookboard.design.query.DesignQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignMapper {

    List<Integer> findProjectIdsBySection(DesignQuery query);

    List<DesignModel> findProjectByQuery(DesignQuery query);
}
