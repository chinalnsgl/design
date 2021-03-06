package com.zw.design.modules.overview.meeting.repository;

import com.zw.design.modules.overview.meeting.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {

    Comment findByMeeting_IdAndUser_Id(Integer MeetingId, Integer UserId);
}
