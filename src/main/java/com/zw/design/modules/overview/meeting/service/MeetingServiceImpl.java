package com.zw.design.modules.overview.meeting.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.overview.meeting.entity.Meeting;
import com.zw.design.modules.system.log.service.LogService;
import com.zw.design.modules.overview.meeting.query.MeetingQuery;
import com.zw.design.modules.overview.meeting.repository.MeetingRepository;
import com.zw.design.modules.system.user.entity.SysUser;
import org.apache.shiro.SecurityUtils;
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

    // 按条件查询会议表格模型数据
    @Override
    public BaseDataTableModel<Meeting> findListByQuery(MeetingQuery query) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(), Sort.by(Sort.Direction.DESC,"id"));
        Page<Meeting> meetingPage = meetingRepository.findAll((Specification<Meeting>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            Predicate userIdPredicate = criteriaBuilder.equal(root.get("user").get("id"), user.getId());
            Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
            Predicate predicate = criteriaBuilder.and(userIdPredicate, statusPredicate);
            Predicate predicate3 = criteriaBuilder.equal(root.get("status").as(Integer.class), 2);
            list.add(criteriaBuilder.or(predicate, predicate3));
            if (null != query.getTitleQuery() && !"".equals(query.getTitleQuery())) {
                list.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + query.getTitleQuery() + "%"));
            }
            if (null != query.getStartTimeQuery() ) {
                list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date").as(Date.class), query.getStartTimeQuery()));
            }
            if (null != query.getEndTimeQuery() ) {
                list.add(criteriaBuilder.lessThanOrEqualTo(root.get("date").as(Date.class), query.getEndTimeQuery()));
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

    // 按ID查询会议
    @Override
    public Meeting findMeetingById(Integer id) {
        return meetingRepository.findById(id).get();
    }

    // 保存会议
    @Override
    public Meeting saveMeeting(Meeting meeting) {
        logService.saveLog("新建会议", meeting.getTitle());
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return null;
        }
        meeting.setUser(user);
        meeting.setContent(meeting.getContent()
                .replaceAll("\r\n|\r|\n|\n\r","<br>")
                .replaceAll(" ", "&ensp;"));
        return meetingRepository.save(meeting);
    }

    // 修改会议状态
    @Override
    public Meeting updateMeetingStatus(Integer id, Integer status) {
        Meeting meeting = findMeetingById(id);
        if (meeting.getStatus() == 2 && status == 1) {
            logService.saveLog("退回会议", meeting.getTitle());
        }
        if (meeting.getStatus() == 1 && status == 2) {
            logService.saveLog("提交会议", meeting.getTitle());
        }
        if (status == 0) {
            logService.saveLog("删除会议", meeting.getTitle());
        }
        meeting.setStatus(status);
        return meetingRepository.save(meeting);
    }

    // 修改会议
    @Override
    public Meeting updateMeeting(Meeting meeting) {
        Meeting meeting1 = findMeetingById(meeting.getId());
        logService.saveLog("修改会议", meeting1, meeting);
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
