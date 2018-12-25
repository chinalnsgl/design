package com.zw.design.modules.integrate.interactive.mapper;

import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.integrate.interactive.query.InteractiveQuery;
import com.zw.design.modules.lookboard.single.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractiveMapper {

    List<Project> findProjectByQuery(InteractiveQuery query);

    List<Message> findMessageByQuery(InteractiveQuery query);
}
