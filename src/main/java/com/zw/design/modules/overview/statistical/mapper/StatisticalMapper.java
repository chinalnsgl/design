package com.zw.design.modules.overview.statistical.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticalMapper {

    // 科室执行中项目数量
    Integer countExecutingProjectBySection(@Param("sectionId") Integer sectionId);

    // 科室未执行项目数量
    Integer countNotStartedProjectBySection(@Param("sectionId")Integer sectionId);

    // 年度项目总数量
    Integer countProjectTotalByYear(@Param("year")Integer year);

    // 年度项目完成数量
    Integer countFinishedProjectByYear(@Param("year")Integer year);

    // 年度项目未完成数量
    Integer countUnFinishedProjectByYear(@Param("year")Integer year);

    // 年度项目取消数量
    Integer countCancelProjectByYear(@Param("year")Integer year);

    // 项目设计任务年度完成数量
    Integer countDesignFinishedProjectByYear(@Param("year")Integer year);

    // 年度项目设计未完成数量
    Integer countDesignUnFinishedProjectByYear(@Param("year")Integer year);

    // 年度项目工艺未完成数量
    Integer countProcessUnFinishedProjectByYear(@Param("year")Integer year);

    // 项目工艺任务年度完成数量
    Integer countProcessFinishedProjectByYear(@Param("year")Integer year);

    // 年度项目生产未完成数量
    Integer countProduceUnFinishedProjectByYear(@Param("year")Integer year);

    // 项目生产任务年度完成数量
    Integer countProduceFinishedProjectByYear(@Param("year")Integer year);

    // 月份项目总数量
    Integer countProjectTotalByMonth(@Param("month")Integer month);

    // 月份项目完成数量
    Integer countFinishedProjectByMonth(@Param("month")Integer month);

    // 月份项目未完成数量
    Integer countUnFinishedProjectByMonth(@Param("month")Integer month);

    // 月份项目取消数量
    Integer countCancelProjectByMonth(@Param("month")Integer month);

    // 月份项目设计完成数量
    Integer countDesignFinishedProjectByMonth(@Param("month")Integer month);

    // 月份项目设计未完成数量
    Integer countDesignUnFinishedProjectByMonth(@Param("month")Integer month);

    // 月份项目工艺未完成数量
    Integer countProcessUnFinishedProjectByMonth(@Param("month")Integer month);

    // 项目工艺任务月份完成数量
    Integer countProcessFinishedProjectByMonth(@Param("month")Integer month);

    // 月份项目生产未完成数量
    Integer countProduceUnFinishedProjectByMonth(@Param("month")Integer month);

    // 项目生产任务月份完成数量
    Integer countProduceFinishedProjectByMonth(@Param("month")Integer month);

}
