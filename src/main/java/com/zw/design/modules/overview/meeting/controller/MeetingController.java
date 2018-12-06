package com.zw.design.modules.overview.meeting.controller;

import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.overview.meeting.entity.Comment;
import com.zw.design.modules.overview.meeting.entity.Meeting;
import com.zw.design.modules.overview.meeting.query.MeetingQuery;
import com.zw.design.modules.overview.meeting.service.CommentService;
import com.zw.design.modules.overview.meeting.service.MeetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/meeting")
public class MeetingController {

    private String prefix = "meeting";

    @Autowired
    private MeetingService meetingService;
    @Autowired
    private CommentService commentService;


    /**
     * 会议列表
     */
    @GetMapping("/list")
    public String meetingList() {
        return prefix + "/list";
    }

    /**
     * 会议列表数据
     */
    @PostMapping("/list")
    @ResponseBody
    public BaseResponse meetingList(MeetingQuery query) {
        BaseDataTableModel<Meeting> dto = meetingService.findListByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 会议详细页面
     */
    @GetMapping("/{id}")
    public String meetingList(@PathVariable("id")Integer id, Model model) {
        Meeting meeting = meetingService.findMeetingById(id);
        model.addAttribute("meeting", meeting);
        return prefix + "/detail";
    }

    /**
     * 创建会议
     */
    @PostMapping("/create")
    @ResponseBody
    public BaseResponse saveMeeting(Meeting meeting) {
        Meeting m = meetingService.saveMeeting(meeting);
        return BaseResponse.toResponse(m.getId());
    }

    /**
     * 删除会议
     */
    @ResponseBody
    @PostMapping("/del")
    public BaseResponse delMeeting(@RequestParam("id")Integer id) {
        Meeting meeting = meetingService.delMeeting(id, 0);
        return BaseResponse.toResponse(meeting);
    }

    /**
     * 退回会议
     */
    @ResponseBody
    @PostMapping("/return")
    public BaseResponse returnMeeting(@RequestParam("id")Integer id, HttpServletRequest request) {
        Meeting meeting = meetingService.findMeetingById(id);
        meeting.setStatus(2);
        meetingService.saveMeeting(meeting);
        return BaseResponse.STATUS_200;
    }

    /**
     * 修改会议
     */
    @ResponseBody
    @PostMapping("/update")
    public BaseResponse updateMeeting(Meeting meeting) {
        Meeting m = meetingService.updateMeeting(meeting);
        return BaseResponse.STATUS_200;
    }

    /**
     * 评价会议
     */
    @PostMapping("/comment")
    @ResponseBody
    public BaseResponse commentMeeting(Integer id, String content, HttpServletRequest request) {
        Meeting meeting = meetingService.findMeetingById(id);
        Comment comment = commentService.saveComment(id, content);
        return BaseResponse.toResponse(comment);
    }

    /**
     * 获取用户评价
     */
    @PostMapping("/user/comment")
    @ResponseBody
    public BaseResponse userComment(Integer id) {
        Comment comment = commentService.findCommentByMeetingIdAndUserId(id);
        return new BaseResponse(200, comment);
    }

    /**
     * 删除用户评价
     */
    @GetMapping("/comment/del/{id}")
    @ResponseBody
    public BaseResponse delComment(@PathVariable("id") Integer id, HttpServletRequest request) {
        Comment comment = commentService.findCommentById(id);
        commentService.delComment(id);
        return BaseResponse.STATUS_200;
    }

}
