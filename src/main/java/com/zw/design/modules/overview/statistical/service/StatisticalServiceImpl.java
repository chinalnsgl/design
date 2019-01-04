package com.zw.design.modules.overview.statistical.service;

import com.zw.design.modules.baseinfosetting.section.entity.Section;
import com.zw.design.modules.baseinfosetting.section.repository.SectionRepository;
import com.zw.design.modules.build.create.repository.ProjectRepository;
import com.zw.design.modules.overview.statistical.mapper.StatisticalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticalServiceImpl implements StatisticalService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private StatisticalMapper statisticalMapper;

    // 科室执行中项目数量
    @Override
    public Integer findProjectCount() {
        return projectRepository.countByStatusNot(1);
    }

    // 科室未执行项目数量
    @Override
    public Integer findFinishedProjectCount() {
        return projectRepository.countByStatus(4);
    }

    // 未完成项目数
    @Override
    public Integer findUnFinishedProjectCount() {
        return projectRepository.countByStatus(2);
    }

    // 已完成设计数
    @Override
    public Integer findFinishedDesignCount() {
        return projectRepository.countByStatusNotAndSectionTaskStatus(1, 2);
    }

    // 未完成设计数
    @Override
    public Integer findUnFinishedDesignCount() {
        return null;
    }

    // 已完成工艺数
    @Override
    public Integer findFinishedProcessCount() {
        return projectRepository.countByStatusNotAndProcessTaskStatus(1, 2);
    }

    // 未完成工艺数
    @Override
    public Integer findUnFinishedProcessCount() {
        return null;
    }

    // 已完成生产数
    @Override
    public Integer findFinishedProduceCount() {
        return projectRepository.countByStatusNotAndProduceTaskStatus(1, 2);
    }

    // 未完成生产数
    @Override
    public Integer findUnFinishedProduceCount() {
        return null;
    }

    // 查询所有科室
    @Override
    public List<Section> findSectionAll() {
        return sectionRepository.findByStatus(1);
    }

    // 科室完成项目数量
    @Override
    public Integer findSectionExecutingProjectCount(Integer id) {
        return statisticalMapper.countExecutingProjectBySection(id);
    }

    // 科室未完成项目数量
    @Override
    public Integer findSectionNotStartedProjectCount(Integer id) {
        return statisticalMapper.countNotStartedProjectBySection(id);
    }

    // 按年份查询项目数量
    @Override
    public Integer findProjectCountByYear(Integer year) {
        return statisticalMapper.countProjectTotalByYear(year);
    }

    // 按年份查询已完成项目数量
    @Override
    public Integer findFinishedProjectCountByYear(Integer year) {
        return statisticalMapper.countFinishedProjectByYear(year);
    }

    // 按年份查询未完成项目数量
    @Override
    public Integer findUnFinishedProjectCountByYear(Integer year) {
        return statisticalMapper.countUnFinishedProjectByYear(year);
    }

    // 按年份查询取消项目数量
    @Override
    public Integer findCancelProjectCountByYear(Integer year) {
        return statisticalMapper.countCancelProjectByYear(year);
    }

    // 按年份查询已完成设计项目数量
    @Override
    public Integer findFinishedDesignCountByYear(Integer year) {
        return statisticalMapper.countDesignFinishedProjectByYear(year);
    }

    // 按年份查询未完成设计项目数量
    @Override
    public Integer findUnFinishedDesignCountByYear(Integer year) {
        return statisticalMapper.countDesignUnFinishedProjectByYear(year);
    }

    // 按年份查询已完成工艺项目数量
    @Override
    public Integer findFinishedProcessCountByYear(Integer year) {
        return statisticalMapper.countProcessFinishedProjectByYear(year);
    }

    // 按年份查询未完成工艺项目数量
    @Override
    public Integer findUnFinishedProcessCountByYear(Integer year) {
        return statisticalMapper.countProcessUnFinishedProjectByYear(year);
    }

    // 按年份查询已完成生产项目数量
    @Override
    public Integer findFinishedProduceCountByYear(Integer year) {
        return statisticalMapper.countProduceFinishedProjectByYear(year);
    }

    // 按年份查询未完成生产项目数量
    @Override
    public Integer findUnFinishedProduceCountByYear(Integer year) {
        return statisticalMapper.countProduceUnFinishedProjectByYear(year);
    }

    // 按月份查询项目数量
    @Override
    public Integer findProjectCountByMonth(Integer month) {
        return statisticalMapper.countProjectTotalByMonth(month);
    }

    // 按月份查询已完成项目数量
    @Override
    public Integer findFinishedProjectCountByMonth(Integer month) {
        return statisticalMapper.countFinishedProjectByMonth(month);
    }

    // 按月份查询未完成项目数量
    @Override
    public Integer findUnFinishedProjectCountByMonth(Integer month) {
        return statisticalMapper.countUnFinishedProjectByMonth(month);
    }

    // 按月份查询取消项目数量
    @Override
    public Integer findCancelProjectCountByMonth(Integer month) {
        return statisticalMapper.countCancelProjectByMonth(month);
    }

    // 按月份查询已完成设计项目数量
    @Override
    public Integer findFinishedDesignCountByMonth(Integer month) {
        return statisticalMapper.countDesignFinishedProjectByMonth(month);
    }

    // 按月份查询未完成设计项目数量
    @Override
    public Integer findUnFinishedDesignCountByMonth(Integer month) {
        return statisticalMapper.countDesignUnFinishedProjectByMonth(month);
    }

    // 按月份查询已完成工艺项目数量
    @Override
    public Integer findFinishedProcessCountByMonth(Integer month) {
        return statisticalMapper.countProcessFinishedProjectByMonth(month);
    }

    // 按月份查询未完成工艺项目数量
    @Override
    public Integer findUnFinishedProcessCountByMonth(Integer month) {
        return statisticalMapper.countProcessUnFinishedProjectByMonth(month);
    }

    // 按月份查询已完成生产项目数量
    @Override
    public Integer findFinishedProduceCountByMonth(Integer month) {
        return statisticalMapper.countProduceFinishedProjectByMonth(month);
    }

    // 按月份查询未完成生产项目数量
    @Override
    public Integer findUnFinishedProduceCountByMonth(Integer month) {
        return statisticalMapper.countProduceUnFinishedProjectByMonth(month);
    }
}
