package com.zw.design.modules.overview.meeting.repository;

import com.zw.design.modules.overview.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MeetingRepository extends JpaRepository<Meeting, Integer>, JpaSpecificationExecutor<Meeting> {


}
