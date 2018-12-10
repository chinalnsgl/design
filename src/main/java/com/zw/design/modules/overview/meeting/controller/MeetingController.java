package com.zw.design.modules.overview.meeting.controller;

import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.overview.meeting.entity.Comment;
import com.zw.design.modules.overview.meeting.entity.Meeting;
import com.zw.design.modules.overview.meeting.query.MeetingQuery;
import com.zw.design.modules.overview.meeting.service.CommentService;
import com.zw.design.modules.overview.meeting.service.MeetingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/meeting")
public class MeetingController {

    private String prefix = "overview/meeting";

    @Autowired
    private MeetingService meetingService;
    @Autowired
    private CommentService commentService;


    /**
     * 会议列表
     */
    @GetMapping("/meetings")
    @RequiresPermissions({"meeting:list"})
    public String meetings() {
        return prefix + "/list";
    }

    /**
     * 会议列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"meeting:list"})
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
    @RequiresPermissions({"meeting:list"})
    public String meetingDetail(@PathVariable("id")Integer id, Model model) {
        Meeting meeting = meetingService.findMeetingById(id);
        model.addAttribute("meeting", meeting);
        return prefix + "/detail";
    }

    /**
     * 创建会议
     */
    @ResponseBody
    @PostMapping("/create")
    @RequiresPermissions({"meeting:create"})
    public BaseResponse saveMeeting(Meeting meeting) {
        Meeting m = meetingService.saveMeeting(meeting);
        return BaseResponse.toResponse(m.getId());
    }

    /**
     * 删除会议
     */
    @ResponseBody
    @PostMapping("/del")
    @RequiresPermissions({"meeting:del"})
    public BaseResponse delMeeting(@RequestParam("id")Integer id) {
        Meeting meeting = meetingService.updateMeetingStatus(id, 0);
        return BaseResponse.toResponse(meeting);
    }

    /**
     * 提交会议
     */
    @ResponseBody
    @PostMapping("/commit")
    @RequiresPermissions({"meeting:commit"})
    public BaseResponse commitMeeting(@RequestParam("id")Integer id) {
        Meeting meeting = meetingService.updateMeetingStatus(id, 2);
        return BaseResponse.toResponse(meeting);
    }

    /**
     * 退回会议
     */
    @ResponseBody
    @PostMapping("/return")
    @RequiresPermissions({"meeting:return"})
    public BaseResponse returnMeeting(@RequestParam("id")Integer id) {
        Meeting meeting = meetingService.updateMeetingStatus(id, 1);
        return BaseResponse.toResponse(meeting);
    }

    /**
     * 修改会议
     */
    @ResponseBody
    @PostMapping("/update")
    @RequiresPermissions({"meeting:update"})
    public BaseResponse updateMeeting(Meeting meeting) {
        Meeting m = meetingService.updateMeeting(meeting);
        return BaseResponse.toResponse(m);
    }

    /**
     * 评价会议
     */
    @ResponseBody
    @PostMapping("/comment")
    @RequiresPermissions({"meeting:comment"})
    public BaseResponse commentMeeting(Integer id, String content) {
        Comment comment = commentService.saveComment(id, content);
        return BaseResponse.toResponse(comment);
    }

    /**
     * 删除用户评价
     */
    @ResponseBody
    @GetMapping("/comment/del/{id}")
    @RequiresPermissions({"comment:del"})
    public BaseResponse delComment(@PathVariable("id") Integer id) {
        commentService.delComment(id);
        return BaseResponse.STATUS_200;
    }

}
