package com.zw.design.modules.build.distribute.repository;

import com.zw.design.modules.build.distribute.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MessageRepository extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {

}
