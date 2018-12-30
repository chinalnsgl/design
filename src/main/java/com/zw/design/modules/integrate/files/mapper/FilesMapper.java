package com.zw.design.modules.integrate.files.mapper;

import com.zw.design.modules.integrate.files.model.FilesModel;
import com.zw.design.modules.integrate.files.query.FilesQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilesMapper {

    List<FilesModel> findProjectByQuery(FilesQuery query);
}
