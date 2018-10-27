package com.zw.design.controller;

import com.zw.design.aspect.LogAnnotation;
import com.zw.design.dto.*;
import com.zw.design.entity.DeptTask;
import com.zw.design.entity.ProduceTask;
import com.zw.design.entity.Project;
import com.zw.design.form.ProjectForm;
import com.zw.design.form.ProjectSendForm;
import com.zw.design.query.ProjectQuery;
import com.zw.design.query.TaskQuery;
import com.zw.design.service.ProjectService;
import com.zw.design.service.TaskService;
import com.zw.design.service.impl.ProcessService;
import com.zw.design.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

@Controller
@Slf4j
@RequestMapping("/project")
public class ProjectController {

    private String prefix = "project";
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessService processService;
    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 创建项目页面
     */
    @GetMapping("/create")
    public String create() {
        return prefix + "/create";
    }

    /**
     * 修改项目页面
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        Project p = projectService.findProjectById(id);
        model.addAttribute("project", p);
        return prefix + "/update";
    }

    /**
     * 项目编号唯一验证
     */
    @ResponseBody
    @PostMapping("/checkCodeUnique")
    public ValidResponse checkCodeUnique(@RequestParam("code") String code) {
        Project project = projectService.findByCode(code);
        return ValidResponse.toResponse(project);
    }

    /**
     * 删除项目
     */
    @ResponseBody
    @PostMapping("/del")
    @LogAnnotation(action = "删除项目")
    public BaseResponse create(@RequestParam("id")Integer id) {
        projectService.delProject(id);
        return BaseResponse.STATUS_200;
    }

    /**
     * 创建项目
     */
    @ResponseBody
    @PostMapping("/create")
    @LogAnnotation(action = "创建项目")
    public BaseResponse create(Project project) {
        Project p = projectService.saveProject(project);
        return BaseResponse.toResponse(p.getId());
    }

    /**
     * 修改项目
     */
    @ResponseBody
    @PostMapping("/update")
    @LogAnnotation(action = "修改项目")
    public BaseResponse update(ProjectForm project) {
        Project p = projectService.updateProject(project);
        return BaseResponse.toResponse(p.getId());
    }

    /**
     * 下达任务单
     */
    @ResponseBody
    @PostMapping("/send")
    @LogAnnotation(action = "下达任务单")
    public BaseResponse send(ProjectSendForm form) {
        if (projectService.sendTask(form)) {
            return new BaseResponse();
        } else {
            return BaseResponse.STATUS_400;
        }
    }

    /**
     * 未下达任务单的项目列表
     */
    @ResponseBody
    @PostMapping("/list/notSend")
    public BaseResponse projectListForNotSend(ProjectQuery query) {
        DataTablesCommonDto<Project> dto = projectService.findProjectsForNotSendByCriteria(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 已下达任务单的项目列表
     */
    @ResponseBody
    @PostMapping("/list/send")
    public BaseResponse projectListForSend(ProjectQuery query) {
        DataTablesCommonDto<Project> dto = projectService.findProjectsForSendByCriteria(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 多项目看板页面
     */
    @GetMapping("/many")
    public String many() {
        return prefix + "/many";
    }

    /**
     * 多项目列表数据
     */
    @ResponseBody
    @PostMapping("/many")
    public BaseResponse many(ProjectQuery query) {
        if (query.getDepartmentQuery() != null && !"".equals(query.getDepartmentQuery())) {
            String[] strings = query.getDepartmentQuery().split(",");
            query.setType(Integer.parseInt(strings[0]));
            query.setNum(Integer.parseInt(strings[1]));
        }
        DataTablesCommonDto<TaskDto> dto = taskService.findByCriteria(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 取消设计完成进度
     */
    @ResponseBody
    @PostMapping("/dept/cancel")
    @LogAnnotation(action = "取消科室完成进度")
    public BaseResponse cancelDept(@RequestParam("id")Integer id) {
        DeptTask deptTask = projectService.cancelDept(id);
        return BaseResponse.toResponse(deptTask.getId());
    }

    /**
     * 取消生产完成进度
     */
    @ResponseBody
    @PostMapping("/produce/cancel")
    @LogAnnotation(action = "取消生产部完成进度")
    public BaseResponse cancelProduce(@RequestParam("id")Integer id) {
        ProduceTask produceTask = projectService.cancelProduce(id);
        return BaseResponse.toResponse(produceTask.getId());
    }

    /**
     * 修改状态
     */
    @ResponseBody
    @PostMapping("/status")
    @LogAnnotation(action = "暂停/启动/取消/恢复项目")
    public BaseResponse status(@RequestParam("id")Integer id, @RequestParam("status") Integer status, @RequestParam("comment") String comment) {
        Project p = projectService.updateStatus(id,status, comment);
        return BaseResponse.toResponse(p.getId());
    }

    /**
     * 单项目列表
     */
    @GetMapping("/single/{id}")
    public ModelAndView single(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("project/single");
        Project p = projectService.findProjectById(id);
        modelAndView.addObject("project", p);
        return modelAndView;
    }

    /**
     * 编辑生产任务进度
     */
    @ResponseBody
    @PostMapping("/task/produce/edit")
    @LogAnnotation(action = "编辑生产任务进度")
    public BaseResponse editProduceTask(ProduceTask produceTask) {
        if (produceTask.getComment() != null) {
            produceTask.setComment(produceTask.getComment().replaceAll("\r\n|\r|\n|\n\r",""));
        }
        ProduceTask t = taskService.saveProduceTask(produceTask);
        return BaseResponse.toResponse(t.getId());
    }

    /**
     * 编辑科室任务进度
     */
    @ResponseBody
    @PostMapping("/task/dept/edit")
    @LogAnnotation(action = "编辑设计科室任务进度")
    public BaseResponse editDeptTask(DeptTask deptTask) {
        if (deptTask.getComment() != null) {
            deptTask.setComment(deptTask.getComment().replaceAll("\r\n|\r|\n|\n\r",""));
        }
        DeptTask t = taskService.saveDeptTask(deptTask);
        return BaseResponse.toResponse(t.getId());
    }

    /**
     * 科室看板页面
     */
    @GetMapping("/depts")
    public String depts() {
        return prefix + "/dept/list";
    }

    /**
     * 科室看板数据
     */
    @ResponseBody
    @PostMapping("/dept/list")
    public BaseResponse deptTaskList(TaskQuery query) {
        DataTablesCommonDto<DeptTaskDto> dto = taskService.findTasksByCriteria(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 科室看板统计信息
     */
    @ResponseBody
    @PostMapping("/dept/collect")
    public BaseResponse deptTaskCollect(TaskQuery query) {
        List<CollectDto> dto = taskService.findDeptTaskCollect(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 工艺进度页面
     */
    @GetMapping("/process")
    public String process() {
        return prefix + "/produce/process";
    }

    /**
     * 工艺进度数据
     */
    @ResponseBody
    @PostMapping("/process/list")
    public BaseResponse processList(TaskQuery query) {
        DataTablesCommonDto<DeptTaskDto> dto = taskService.findProcessList(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 生产进度页面
     */
    @GetMapping("/produce")
    public String produce() {
        return prefix + "/produce/produce";
    }

    /**
     * 生产进度数据
     */
    @ResponseBody
    @PostMapping("/produce/list")
    public BaseResponse produceList(TaskQuery query) {
        DataTablesCommonDto<DeptTaskDto> dto = taskService.findProduceList(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @ResponseBody
    @PostMapping("/upload")
    @LogAnnotation(action = "上传")
    public BaseResponse upload(@RequestParam("file") MultipartFile[] file, @RequestParam("id") Integer id, @RequestParam("code") String code) {

        if (null != file && file.length > 0) {
            //遍历并保存文件
            for (MultipartFile f : file) {
                if (!f.isEmpty()) {
                    //取得当前上传文件的文件名称
                    String fileName = f.getOriginalFilename();
                    String path = uploadPath + code + "/";
                    File dir = new File(path);
                    if (!dir.exists()) {
                        boolean mkdir = dir.mkdirs();
                        if (!mkdir) {
                             return BaseResponse.STATUS_400;
                        }
                    }
                    File saveFile = new File(path + fileName);
                    try {
                        f.transferTo(saveFile);
                        projectService.saveImage(id, fileName, path + fileName);
                    } catch (Exception e) {
                        return BaseResponse.STATUS_400;
                    }
                }
            }
        } else {
            return BaseResponse.STATUS_400;
        }
        return BaseResponse.STATUS_200;
    }

    @GetMapping("/download")
    @LogAnnotation(action = "下载")
    public void download(HttpServletResponse response, @RequestParam("ids") Integer[] ids, @RequestParam("code")String code) {
        projectService.download(response, ids, code);
    }

    @ResponseBody
    @PostMapping("/delFile")
    @LogAnnotation(action = "删除文件")
    public BaseResponse delFile(@RequestParam("ids") Integer[] ids) {
        projectService.delFile(ids);
        return BaseResponse.STATUS_200;
    }

    /**
     * 处理数据
     */
    @ResponseBody
    @GetMapping("/processDate")
    public String processDate() {
        processService.process();
        return "ok";
    }

}
