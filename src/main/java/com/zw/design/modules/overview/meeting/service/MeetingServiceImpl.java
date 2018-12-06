package com.zw.design.modules.overview.meeting.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.overview.meeting.entity.Meeting;
import com.zw.design.modules.system.log.service.LogService;
import com.zw.design.modules.overview.meeting.query.MeetingQuery;
import com.zw.design.modules.overview.meeting.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private LogService logService;

    @Override
    public BaseDataTableModel<Meeting> findListByQuery(MeetingQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(), Sort.by(Sort.Direction.DESC,"id"));
        Page<Meeting> meetingPage = meetingRepository.findAll((Specification<Meeting>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            list.add(criteriaBuilder.greaterThan(root.get("status").as(Integer.class), 0));
            if (null != query.getTopic() && !"".equals(query.getTopic())) {
                list.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + query.getTopic() + "%"));
            }
            if (null != query.getStartTime() ) {
                list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date").as(Date.class), query.getStartTime()));
            }
            if (null != query.getEndTime() ) {
                list.add(criteriaBuilder.lessThanOrEqualTo(root.get("date").as(Date.class), query.getEndTime()));
            }
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        BaseDataTableModel<Meeting> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(meetingPage.getContent());
        baseDataTableModel.setRecordsTotal((int)meetingPage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)meetingPage.getTotalElements());
        return baseDataTableModel;
    }

    @Override
    public Meeting saveMeeting(Meeting meeting) {
        logService.saveLog("新建会议：", meeting.getTitle());
        meeting.setContent(meeting.getContent()
                .replaceAll("\r\n|\r|\n|\n\r","<br>")
                .replaceAll(" ", "&ensp;"));
        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting delMeeting(Integer id, Integer status) {
        Meeting meeting = findMeetingById(id);
        logService.saveLog("删除会议", meeting.getTitle());
        meeting.setStatus(status);
        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting findMeetingById(Integer id) {
        return meetingRepository.findById(id).get();
    }

    @Override
    public Meeting updateMeeting(Meeting meeting) {
        Meeting meeting1 = findMeetingById(meeting.getId());
        logService.saveLog("修改会议：", meeting1, meeting);
        meeting1.setStatus(1);
        meeting1.setAddress(meeting.getAddress());
        meeting1.setContent(meeting.getContent()
                .replaceAll("\r\n|\r|\n|\n\r", "<br>")
                .replaceAll(" ", "&ensp;"));
        meeting1.setTitle(meeting.getTitle());
        meeting1.setPersonnel(meeting.getPersonnel());
        meeting1.setDate(meeting.getDate());
        return meetingRepository.save(meeting1);
    }
}
