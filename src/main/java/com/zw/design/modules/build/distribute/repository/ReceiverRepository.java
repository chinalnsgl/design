package com.zw.design.modules.build.distribute.repository;

import com.zw.design.modules.build.distribute.entity.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReceiverRepository extends JpaRepository<Receiver, Integer>, JpaSpecificationExecutor<Receiver> {

    List<Receiver> findByStatusAndUser_Id(Integer status, Integer userId);
}
