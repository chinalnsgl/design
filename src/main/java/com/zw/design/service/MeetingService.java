package com.zw.design.service;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.Meeting;
import com.zw.design.query.MeetingQuery;

public interface MeetingService {
    DataTablesCommonDto<Meeting> findListByQuery(MeetingQuery query);

    Meeting saveMeeting(Meeting meeting);

    Meeting findMeetingById(Integer id);

    Meeting updateMeeting(Meeting meeting);
}
