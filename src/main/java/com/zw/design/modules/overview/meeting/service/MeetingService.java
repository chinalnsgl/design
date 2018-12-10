package com.zw.design.modules.overview.meeting.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.overview.meeting.entity.Meeting;
import com.zw.design.modules.overview.meeting.query.MeetingQuery;

public interface MeetingService {

    // 按条件查询会议表格模型数据
    BaseDataTableModel<Meeting> findListByQuery(MeetingQuery query);

    // 按ID查询会议
    Meeting findMeetingById(Integer id);

    // 保存会议
    Meeting saveMeeting(Meeting meeting);

    // 修改会议状态
    Meeting updateMeetingStatus(Integer id, Integer status);

    // 修改会议
    Meeting updateMeeting(Meeting meeting);
}
