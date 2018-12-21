package com.zw.design.modules.lookboard.single.repository;

import com.zw.design.modules.lookboard.single.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImageRepository extends JpaRepository<Image, Integer>, JpaSpecificationExecutor<Image> {

    Long countByUrl(String url);

}
