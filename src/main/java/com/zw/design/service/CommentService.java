package com.zw.design.service;

import com.zw.design.entity.Comment;

public interface CommentService {

    Comment saveComment(Integer id, String content);

    Comment findCommentByMeetingIdAndUserId(Integer id);

    Comment findCommentById(Integer id);

    void delComment(Integer id);
}
