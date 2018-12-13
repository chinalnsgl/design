package com.zw.design.modules.lookboard.design.entity;

import com.zw.design.modules.lookboard.multi.model.MultiModel;
import com.zw.design.modules.lookboard.multi.query.MultiQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignMapper {

    List<MultiModel> findProjectByQuery(MultiQuery query);
}
