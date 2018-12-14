package com.zw.design.modules.build.distributedesigntask.repository;

import com.zw.design.modules.build.distributedesigntask.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MessageRepository extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {

}