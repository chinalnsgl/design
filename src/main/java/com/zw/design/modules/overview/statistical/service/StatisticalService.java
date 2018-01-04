package com.zw.design.modules.overview.statistical.service;

import com.zw.design.modules.baseinfosetting.section.entity.Section;

import java.util.List;

public interface StatisticalService {

    // 项目总数，不含未下达的项目
    Integer findProjectCount();

    // 已完成项目数
    Integer findFinishedProjectCount();

    // 未完成项目数
    Integer findUnFinishedProjectCount();

    // 已完成设计数
    Integer findFinishedDesignCount();

    // 未完成设计数
    Integer findUnFinishedDesignCount();

    // 已完成工艺数
    Integer findFinishedProcessCount();

    // 未完成工艺数
    Integer findUnFinishedProcessCount();

    // 已完成生产数
    Integer findFinishedProduceCount();

    // 未完成生产数
    Integer findUnFinishedProduceCount();

    // 查询所有科室
    List<Section> findSectionAll();

    // 科室完成项目数量
    Integer findSectionExecutingProjectCount(Integer id);

    // 科室未完成项目数量
    Integer findSectionNotStartedProjectCount(Integer id);

    // 按年份查询项目数量
    Integer findProjectCountByYear(Integer year);

    // 按年份查询已完成项目数量
    Integer findFinishedProjectCountByYear(Integer year);

    // 查完成项目哪年创建多少个
    String countFinishedProjectByYearGroupCreateTime(Integer year);

    // 按年份查询未完成项目数量
    Integer findUnFinishedProjectCountByYear(Integer year);

    // 按年份查询取消项目数量
    Integer findCancelProjectCountByYear(Integer year);

    // 按年份查询已完成设计项目数量
    Integer findFinishedDesignCountByYear(Integer year);

    // 查完成设计项目哪年创建多少个
    String countDesignFinishedProjectByYearGroupCreateTime(Integer year);

    // 按年份查询未完成设计项目数量
    Integer findUnFinishedDesignCountByYear(Integer year);

    // 按年份查询已完成工艺项目数量
    Integer findFinishedProcessCountByYear(Integer year);

    // 查完成工艺项目哪年创建多少个
    String countProcessFinishedProjectByYearGroupCreateTime(Integer year);

    // 按年份查询未完成工艺项目数量
    Integer findUnFinishedProcessCountByYear(Integer year);

    // 按年份查询已完成生产项目数量
    Integer findFinishedProduceCountByYear(Integer year);

    // 查完成生产项目哪年创建多少个
    String countProduceFinishedProjectByYearGroupCreateTime(Integer year);

    // 按年份查询未完成生产项目数量
    Integer findUnFinishedProduceCountByYear(Integer year);

    // 按月份查询项目数量
    Integer findProjectCountByMonth(Integer month);

    // 按月份查询已完成项目数量
    Integer findFinishedProjectCountByMonth(Integer month);

    // 按月份查询未完成项目数量
    Integer findUnFinishedProjectCountByMonth(Integer month);

    // 按月份查询取消项目数量
    Integer findCancelProjectCountByMonth(Integer month);

    // 按月份查询已完成设计项目数量
    Integer findFinishedDesignCountByMonth(Integer month);

    // 按月份查询未完成设计项目数量
    Integer findUnFinishedDesignCountByMonth(Integer month);

    // 按月份查询已完成工艺项目数量
    Integer findFinishedProcessCountByMonth(Integer month);

    // 按月份查询未完成工艺项目数量
    Integer findUnFinishedProcessCountByMonth(Integer month);

    // 按月份查询已完成生产项目数量
    Integer findFinishedProduceCountByMonth(Integer month);

    // 按月份查询未完成生产项目数量
    Integer findUnFinishedProduceCountByMonth(Integer month);
}
