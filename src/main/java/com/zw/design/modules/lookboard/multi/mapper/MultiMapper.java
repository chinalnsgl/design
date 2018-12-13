package com.zw.design.modules.lookboard.multi.mapper;

import com.zw.design.modules.lookboard.multi.model.MultiModel;
import com.zw.design.modules.lookboard.multi.query.MultiQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultiMapper {

    List<MultiModel> findProjectByQuery (MultiQuery query);
}
