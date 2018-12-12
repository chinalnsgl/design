package com.zw.design.modules.build.distributedesigntask.repository;

import com.zw.design.modules.build.distributedesigntask.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImageRepository extends JpaRepository<Image, Integer>, JpaSpecificationExecutor<Image> {
    Long countByUrl(String url);

    Integer countByProject_IdAndType(Integer id, Integer type);
}
