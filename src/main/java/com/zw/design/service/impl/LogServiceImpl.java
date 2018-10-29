package com.zw.design.service.impl;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.LogInfo;
import com.zw.design.entity.SysUser;
import com.zw.design.query.LogQuery;
import com.zw.design.repository.LogInfoRepository;
import com.zw.design.service.LogService;
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
public class LogServiceImpl implements LogService {

    @Autowired
    private LogInfoRepository logInfoRepository;

    @Override
    public LogInfo saveLog(LogInfo logInfo) {
        return logInfoRepository.save(logInfo);
    }

    @Override
    public LogInfo saveLog(String content, HttpServletRequest request) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return null;
        }

        LogInfo logInfo = new LogInfo();
        logInfo.setAccount(user.getUserName());
        logInfo.setName(user.getName());
        logInfo.setOperationName(content);
        logInfo.setOperationTime(new Date());
        logInfo.setAddress(request.getRemoteAddr());
        logInfoRepository.save(logInfo);
        return logInfo;
    }

    @Override
    public DataTablesCommonDto<LogInfo> findLogByCriteria(LogQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(), Sort.by(Sort.Direction.DESC,"id"));
        Page<LogInfo> logPage = logInfoRepository.findAll((Specification<LogInfo>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<Predicate>();
            if (null != query.getName() && !"".equals(query.getName())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getName() + "%"));
            }
            if (null != query.getStartTime() ) {
                list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("operationTime").as(Date.class), query.getStartTime()));
            }
            if (null != query.getEndTime() ) {
                list.add(criteriaBuilder.lessThanOrEqualTo(root.get("operationTime").as(Date.class), query.getEndTime()));
            }
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        DataTablesCommonDto<LogInfo> dataTablesCommonDto = new DataTablesCommonDto<>();
        dataTablesCommonDto.setDraw(query.getDraw());
        dataTablesCommonDto.setData(logPage.getContent());
        dataTablesCommonDto.setRecordsTotal((int)logPage.getTotalElements());
        dataTablesCommonDto.setRecordsFiltered((int)logPage.getTotalElements());
        return dataTablesCommonDto;
    }
}
