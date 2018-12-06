package com.zw.design.modules.overview.meeting.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.overview.meeting.entity.Meeting;
import com.zw.design.modules.overview.meeting.query.MeetingQuery;

public interface MeetingService {
    BaseDataTableModel<Meeting> findListByQuery(MeetingQuery query);

    Meeting saveMeeting(Meeting meeting);

    Meeting delMeeting(Integer id, Integer status);

    Meeting updateMeeting(Meeting meeting);

    Meeting findMeetingById(Integer id);
}
