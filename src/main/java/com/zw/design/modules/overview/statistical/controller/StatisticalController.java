package com.zw.design.modules.overview.statistical.controller;

import com.zw.design.base.BaseResponse;
import com.zw.design.modules.baseinfosetting.section.entity.Section;
import com.zw.design.modules.overview.statistical.model.StatisticalModel;
import com.zw.design.modules.overview.statistical.service.StatisticalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/overview/statistical")
public class StatisticalController {

    private String prefix = "overview/statistical";

    @Autowired
    private StatisticalService statisticalService;

    /**
     * 统计页面
     */
    @GetMapping("/page")
    @RequiresPermissions({"statistical:list"})
    public String statisticalPage(Model model) {
        Integer projectTotalCount = statisticalService.findProjectCount();
        Integer finishedProjectCount = statisticalService.findFinishedProjectCount();
        Integer unFinishedProjectCount = statisticalService.findUnFinishedProjectCount();
        Integer cancelProjectCount = projectTotalCount - finishedProjectCount - unFinishedProjectCount;
        Integer finishedDesignCount = statisticalService.findFinishedDesignCount();
        Integer unFinishedDesignCount = projectTotalCount - cancelProjectCount - finishedDesignCount;
        Integer finishedProcessCount = statisticalService.findFinishedProcessCount();
        Integer unFinishedProcessCount = projectTotalCount - cancelProjectCount - finishedProcessCount;
        Integer finishedProduceCount = statisticalService.findFinishedProduceCount();
        Integer unFinishedProduceCount = projectTotalCount - cancelProjectCount - finishedProduceCount;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        Integer projectTotalCountByYear = statisticalService.findProjectCountByYear(year);
        Integer finishedProjectCountByYear = statisticalService.findFinishedProjectCountByYear(year);
        Integer unFinishedProjectCountByYear = statisticalService.findUnFinishedProjectCountByYear(year);
        Integer cancelProjectCountByYear = statisticalService.findCancelProjectCountByYear(year);
        Integer finishedDesignCountByYear = statisticalService.findFinishedDesignCountByYear(year);
        Integer unFinishedDesignCountByYear = statisticalService.findUnFinishedDesignCountByYear(year);
        Integer finishedProcessCountByYear = statisticalService.findFinishedProcessCountByYear(year);
        Integer unFinishedProcessCountByYear = statisticalService.findUnFinishedProcessCountByYear(year);
        Integer finishedProduceCountByYear = statisticalService.findFinishedProduceCountByYear(year);
        Integer unFinishedProduceCountByYear = statisticalService.findUnFinishedProduceCountByYear(year);

        Integer projectTotalCountByMonth = statisticalService.findProjectCountByMonth(month);
        Integer finishedProjectCountByMonth = statisticalService.findFinishedProjectCountByMonth(month);
        Integer unFinishedProjectCountByMonth = statisticalService.findUnFinishedProjectCountByMonth(month);
        Integer cancelProjectCountByMonth = statisticalService.findCancelProjectCountByMonth(month);
        Integer finishedDesignCountByMonth = statisticalService.findFinishedDesignCountByMonth(month);
        Integer unFinishedDesignCountByMonth = statisticalService.findUnFinishedDesignCountByMonth(month);
        Integer finishedProcessCountByMonth = statisticalService.findFinishedProcessCountByMonth(month);
        Integer unFinishedProcessCountByMonth = statisticalService.findUnFinishedProcessCountByMonth(month);
        Integer finishedProduceCountByMonth = statisticalService.findFinishedProduceCountByMonth(month);
        Integer unFinishedProduceCountByMonth = statisticalService.findUnFinishedProduceCountByMonth(month);

        model.addAttribute("projectCount", statisticalService.findProjectCount());
        model.addAttribute("finishedProjectCount", finishedProjectCount);
        model.addAttribute("unFinishedProjectCount", unFinishedProjectCount);
        model.addAttribute("cancelProjectCount", cancelProjectCount);
        model.addAttribute("finishedDesignCount", finishedDesignCount);
        model.addAttribute("unFinishedDesignCount", unFinishedDesignCount);
        model.addAttribute("finishedProcessCount", finishedProcessCount);
        model.addAttribute("unFinishedProcessCount", unFinishedProcessCount);
        model.addAttribute("finishedProduceCount", finishedProduceCount);
        model.addAttribute("unFinishedProduceCount", unFinishedProduceCount);

        model.addAttribute("projectCountByYear", projectTotalCountByYear);
        model.addAttribute("finishedProjectCountByYear", finishedProjectCountByYear);
        model.addAttribute("unFinishedProjectCountByYear", unFinishedProjectCountByYear);
        model.addAttribute("cancelProjectCountByYear", cancelProjectCountByYear);
        model.addAttribute("finishedDesignCountByYear", finishedDesignCountByYear);
        model.addAttribute("unFinishedDesignCountByYear", unFinishedDesignCountByYear);
        model.addAttribute("finishedProcessCountByYear", finishedProcessCountByYear);
        model.addAttribute("unFinishedProcessCountByYear", unFinishedProcessCountByYear);
        model.addAttribute("finishedProduceCountByYear", finishedProduceCountByYear);
        model.addAttribute("unFinishedProduceCountByYear", unFinishedProduceCountByYear);

        model.addAttribute("projectCountByMonth", projectTotalCountByMonth);
        model.addAttribute("finishedProjectCountByMonth", finishedProjectCountByMonth);
        model.addAttribute("unFinishedProjectCountByMonth", unFinishedProjectCountByMonth);
        model.addAttribute("cancelProjectCountByMonth", cancelProjectCountByMonth);
        model.addAttribute("finishedDesignCountByMonth", finishedDesignCountByMonth);
        model.addAttribute("unFinishedDesignCountByMonth", unFinishedDesignCountByMonth);
        model.addAttribute("finishedProcessCountByMonth", finishedProcessCountByMonth);
        model.addAttribute("unFinishedProcessCountByMonth", unFinishedProcessCountByMonth);
        model.addAttribute("finishedProduceCountByMonth", finishedProduceCountByMonth);
        model.addAttribute("unFinishedProduceCountByMonth", unFinishedProduceCountByMonth);

        List<Section> sections = statisticalService.findSectionAll();
        List<Integer> sectionExecutingProjectCount = new ArrayList<>();
        List<Integer> sectionNotStartedProjectCount = new ArrayList<>();
        for (Section section : sections) {
            sectionExecutingProjectCount.add(statisticalService.findSectionExecutingProjectCount(section.getId()));
            sectionNotStartedProjectCount.add(statisticalService.findSectionNotStartedProjectCount(section.getId()));
        }
        model.addAttribute("sectionExecutingProjectCount", sectionExecutingProjectCount.toArray(new Integer[sectionExecutingProjectCount.size()]));
        model.addAttribute("sectionNotStartedProjectCount", sectionNotStartedProjectCount.toArray(new Integer[sectionNotStartedProjectCount.size()]));

        return prefix + "/detail";
    }

    @ResponseBody
    @GetMapping("/year/project")
    @RequiresPermissions({"statistical:list"})
    public BaseResponse yearProjectCount(Integer year) {
        StatisticalModel model = new StatisticalModel();
        model.setTotalCount(statisticalService.findProjectCountByYear(year));
        model.setFinishedCount(statisticalService.findFinishedProjectCountByYear(year));
        model.setUnFinishedCount(statisticalService.findUnFinishedProjectCountByYear(year));
        model.setCancelCount(statisticalService.findCancelProjectCountByYear(year));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(model);
        return baseResponse;
    }

    @ResponseBody
    @GetMapping("/year/design")
    @RequiresPermissions({"statistical:list"})
    public BaseResponse yearDesignProjectCount(Integer year) {
        StatisticalModel model = new StatisticalModel();
        model.setTotalCount(statisticalService.findProjectCountByYear(year));
        model.setFinishedCount(statisticalService.findFinishedDesignCountByYear(year));
        model.setUnFinishedCount(statisticalService.findUnFinishedDesignCountByYear(year));
        model.setCancelCount(statisticalService.findCancelProjectCountByYear(year));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(model);
        return baseResponse;
    }

    @ResponseBody
    @GetMapping("/year/process")
    @RequiresPermissions({"statistical:list"})
    public BaseResponse yearProcessProjectCount(Integer year) {
        StatisticalModel model = new StatisticalModel();
        model.setTotalCount(statisticalService.findProjectCountByYear(year));
        model.setFinishedCount(statisticalService.findFinishedProcessCountByYear(year));
        model.setUnFinishedCount(statisticalService.findUnFinishedProcessCountByYear(year));
        model.setCancelCount(statisticalService.findCancelProjectCountByYear(year));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(model);
        return baseResponse;
    }

    @ResponseBody
    @GetMapping("/year/produce")
    @RequiresPermissions({"statistical:list"})
    public BaseResponse yearProduceProjectCount(Integer year) {
        StatisticalModel model = new StatisticalModel();
        model.setTotalCount(statisticalService.findProjectCountByYear(year));
        model.setFinishedCount(statisticalService.findFinishedProduceCountByYear(year));
        model.setUnFinishedCount(statisticalService.findUnFinishedProduceCountByYear(year));
        model.setCancelCount(statisticalService.findCancelProjectCountByYear(year));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(model);
        return baseResponse;
    }

    @ResponseBody
    @GetMapping("/month/project")
    @RequiresPermissions({"statistical:list"})
    public BaseResponse monthProjectCount(Integer month) {
        StatisticalModel model = new StatisticalModel();
        model.setTotalCount(statisticalService.findProjectCountByMonth(month));
        model.setFinishedCount(statisticalService.findFinishedProjectCountByMonth(month));
        model.setUnFinishedCount(statisticalService.findUnFinishedProjectCountByMonth(month));
        model.setCancelCount(statisticalService.findCancelProjectCountByMonth(month));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(model);
        return baseResponse;
    }

    @ResponseBody
    @GetMapping("/month/design")
    @RequiresPermissions({"statistical:list"})
    public BaseResponse monthDesignProjectCount(Integer month) {
        StatisticalModel model = new StatisticalModel();
        model.setTotalCount(statisticalService.findProjectCountByMonth(month));
        model.setFinishedCount(statisticalService.findFinishedDesignCountByMonth(month));
        model.setUnFinishedCount(statisticalService.findUnFinishedDesignCountByMonth(month));
        model.setCancelCount(statisticalService.findCancelProjectCountByMonth(month));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(model);
        return baseResponse;
    }

    @ResponseBody
    @GetMapping("/month/process")
    @RequiresPermissions({"statistical:list"})
    public BaseResponse monthProcessProjectCount(Integer month) {
        StatisticalModel model = new StatisticalModel();
        model.setTotalCount(statisticalService.findProjectCountByMonth(month));
        model.setFinishedCount(statisticalService.findFinishedProcessCountByMonth(month));
        model.setUnFinishedCount(statisticalService.findUnFinishedProcessCountByMonth(month));
        model.setCancelCount(statisticalService.findCancelProjectCountByMonth(month));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(model);
        return baseResponse;
    }

    @ResponseBody
    @GetMapping("/month/produce")
    @RequiresPermissions({"statistical:list"})
    public BaseResponse monthProduceProjectCount(Integer month) {
        StatisticalModel model = new StatisticalModel();
        model.setTotalCount(statisticalService.findProjectCountByMonth(month));
        model.setFinishedCount(statisticalService.findFinishedProduceCountByMonth(month));
        model.setUnFinishedCount(statisticalService.findUnFinishedProduceCountByMonth(month));
        model.setCancelCount(statisticalService.findCancelProjectCountByMonth(month));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(model);
        return baseResponse;
    }

}
