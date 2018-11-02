package com.zw.design.service.impl;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.LogInfo;
import com.zw.design.entity.Meeting;
import com.zw.design.entity.SysUser;
import com.zw.design.query.LogQuery;
import com.zw.design.query.MeetingQuery;
import com.zw.design.repository.CommentRepository;
import com.zw.design.repository.LogInfoRepository;
import com.zw.design.repository.MeetingRepository;
import com.zw.design.service.LogService;
import com.zw.design.service.MeetingService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Override
    public DataTablesCommonDto<Meeting> findListByQuery(MeetingQuery query) {
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
        DataTablesCommonDto<Meeting> dataTablesCommonDto = new DataTablesCommonDto<>();
        dataTablesCommonDto.setDraw(query.getDraw());
        dataTablesCommonDto.setData(meetingPage.getContent());
        dataTablesCommonDto.setRecordsTotal((int)meetingPage.getTotalElements());
        dataTablesCommonDto.setRecordsFiltered((int)meetingPage.getTotalElements());
        return dataTablesCommonDto;
    }

    @Override
    public Meeting saveMeeting(Meeting meeting) {
        meeting.setContent(meeting.getContent()
                .replaceAll("\r\n|\r|\n|\n\r","<br>")
                .replaceAll(" ", "&ensp;"));
        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting findMeetingById(Integer id) {
        return meetingRepository.findById(id).get();
    }

    @Override
    public Meeting updateMeeting(Meeting meeting) {
        Meeting meeting1 = meetingRepository.findById(meeting.getId()).get();
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
