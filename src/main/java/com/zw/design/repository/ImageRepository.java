package com.zw.design.repository;

import com.zw.design.entity.Image;
import com.zw.design.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImageRepository extends JpaRepository<Image, Integer>, JpaSpecificationExecutor<Image> {
    Long countByUrl(String url);

    Integer countByProject_IdAndType(Integer id, Integer type);
}
