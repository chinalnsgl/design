package com.zw.design.modules.lookboard.single.repository;

import com.zw.design.modules.lookboard.single.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {

    List<Message> findByProject_IdAndStatus(Integer id, Integer status);
}
