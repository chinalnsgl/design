package com.zw.design.modules.overview.meeting.service;

import com.zw.design.modules.overview.meeting.entity.Comment;

public interface CommentService {

    Comment saveComment(Integer id, String content);

    Comment findCommentByMeetingIdAndUserId(Integer id);

    Comment findCommentById(Integer id);

    void delComment(Integer id);
}
