package com.zw.design.service.impl;

import com.zw.design.entity.Comment;
import com.zw.design.entity.Meeting;
import com.zw.design.entity.SysUser;
import com.zw.design.repository.CommentRepository;
import com.zw.design.repository.MeetingRepository;
import com.zw.design.service.CommentService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MeetingRepository meetingRepository;

    @Override
    public Comment saveComment(Integer id, String content) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return null;
        }
        Meeting meeting = meetingRepository.findById(id).get();
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setMeeting(meeting);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Comment findCommentByMeetingIdAndUserId(Integer id) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return null;
        }
        return commentRepository.findByMeeting_IdAndUser_Id(id, user.getId());
    }

    @Override
    public Comment findCommentById(Integer id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public void delComment(Integer id) {
        commentRepository.deleteById(id);
    }
}
