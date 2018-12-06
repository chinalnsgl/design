package com.zw.design.modules.system.log.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.log.entity.LogInfo;
import com.zw.design.modules.system.user.entity.SysUser;
import com.zw.design.modules.system.log.query.LogQuery;
import com.zw.design.modules.system.log.repository.LogInfoRepository;
import com.zw.design.utils.CompareUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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
    public BaseDataTableModel<LogInfo> findLogByQuery(LogQuery query) {
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
        BaseDataTableModel<LogInfo> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(logPage.getContent());
        baseDataTableModel.setRecordsTotal((int)logPage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)logPage.getTotalElements());
        return baseDataTableModel;
    }

    // 保存日志
    @Override
    public LogInfo saveLog(LogInfo logInfo) {
        return logInfoRepository.save(logInfo);
    }

    // 保存日志
    @Override
    public LogInfo saveLog(String operationName, String operationContent) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return null;
        }
        LogInfo logInfo = new LogInfo();
        logInfo.setAccount(user.getUserName());
        logInfo.setName(user.getName());
        logInfo.setOperationName(operationName);
        logInfo.setOperationTime(new Date());
        logInfo.setOperationContent(operationContent);
        HttpServletRequest request = (HttpServletRequest) RequestContextHolder.getRequestAttributes().resolveReference(RequestAttributes.REFERENCE_REQUEST);
        logInfo.setAddress(request.getRemoteAddr());
        return saveLog(logInfo);
    }

    // 保存日志
    @Override
    public LogInfo saveLog(String operationName, Object before, Object after) {
        return saveLog(operationName, CompareUtil.compare(before, after));
    }
}
