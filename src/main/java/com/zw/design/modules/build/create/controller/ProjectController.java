package com.zw.design.modules.build.create.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.create.service.ProjectService;
import com.zw.design.modules.build.distributedesigntask.entity.DeptTask;
import com.zw.design.modules.build.distributedesigntask.entity.ProduceTask;
import com.zw.design.modules.build.distributedesigntask.form.*;
import com.zw.design.modules.build.distributedesigntask.model.CollectDto;
import com.zw.design.modules.build.distributedesigntask.model.DeptTaskDto;
import com.zw.design.modules.build.distributedesigntask.model.TaskDto;
import com.zw.design.modules.build.distributedesigntask.query.ProjectQuery;
import com.zw.design.modules.build.distributedesigntask.query.TaskQuery;
import com.zw.design.modules.build.distributedesigntask.service.ProcessService;
import com.zw.design.modules.build.distributedesigntask.service.TaskService;
import com.zw.design.modules.system.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/build")
public class ProjectController {

    private String prefix = "build";
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private LogService logService;
    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 创建项目页面
     */
    @GetMapping("/creates")
    @RequiresPermissions({"create:create"})
    public String create() {
        return prefix + "/create/create";
    }

    /**
     * 项目编号唯一验证
     */
    @ResponseBody
    @PostMapping("create/checkCodeUnique")
    @RequiresPermissions({"create:create"})
    public BaseValidResponse checkCodeUnique(@RequestParam("code") String code, @RequestParam(value = "id",required = false) Integer id) {
        Project project = projectService.findByCode(code);
        if (id == null) {
            return BaseValidResponse.toResponse(project);
        }
        if (project == null || project.getId() == id) {
            return BaseValidResponse.SUCCESS;
        }
        return BaseValidResponse.FAILE;
    }

    /**
     * 创建项目
     */
    @PostMapping("/create/create")
    @RequiresPermissions({"create:create"})
    public String create(Project project) {
        project.setCode(project.getCode().trim());
        projectService.saveProject(project);
        return "redirect:/build/ddts";
    }

    /**
     * 删除项目
     */
    @ResponseBody
    @PostMapping("/create/del")
    @RequiresPermissions({"create:del"})
    public BaseResponse create(@RequestParam("id")Integer id) {
        projectService.delProject(id);
        return BaseResponse.STATUS_200;
    }

    /**
     * 修改项目
     */
    @ResponseBody
    @PostMapping("/update")
    public BaseResponse update(ProjectForm project, HttpServletRequest request) {
        Project p = projectService.updateProject(project);
//        logService.saveLog("删除项目：" + p.getName() + " 项目号：" + p.getCode(), request);
        return BaseResponse.toResponse(p.getId());
    }




    /**
     * 已下达任务单的项目列表
     */
    @ResponseBody
    @PostMapping("/list/send")
    public BaseResponse projectListForSend(ProjectQuery query) {
        BaseDataTableModel<Project> dto = projectService.findProjectsForSendByCriteria(query);
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
        BaseDataTableModel<TaskDto> dto = taskService.findByCriteria(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
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
     * 取消设计完成进度
     */
    @ResponseBody
    @PostMapping("/dept/cancel")
    public BaseResponse cancelDept(@RequestParam("id")Integer id, HttpServletRequest request) {
        DeptTask deptTask = projectService.cancelDept(id);
//        logService.saveLog("撤消设计任务进度：【项目名：" + deptTask.getProject().getName() + " 项目号：" + deptTask.getProject().getCode() + " "+ deptTask.getName() + "  任务名：" + deptTask.getStepName() + " 】", request);
        return BaseResponse.toResponse(deptTask.getId());
    }

    /**
     * 取消生产完成进度
     */
    @ResponseBody
    @PostMapping("/produce/cancel")
    public BaseResponse cancelProduce(@RequestParam("id")Integer id, HttpServletRequest request) {
        ProduceTask produceTask = projectService.cancelProduce(id);
//        logService.saveLog("撤消生产任务进度：【项目名：" + produceTask.getProject().getName() + " 项目号：" + produceTask.getProject().getCode() + "  任务名：" + produceTask.getProduceName() + " 】", request);
        return BaseResponse.toResponse(produceTask.getId());
    }

    /**
     * 修改状态
     */
    @ResponseBody
    @PostMapping("/status")
    public BaseResponse status(@RequestParam("id")Integer id, @RequestParam("status") Integer status, @RequestParam("comment") String comment, HttpServletRequest request) {
        Project p = projectService.updateStatus(id,status, comment);
//        logService.saveLog((status == 3 ? "暂停项目：" : ( status == 0 ? "取消项目" : "恢复启动项目：")) + p.getName() + p.getCode(), request);
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
        Integer taskCount = projectService.findTaskFileCount(id);
        Integer signCount = projectService.findSignFileCount(id);
        Integer contractCount = projectService.findContractFileCount(id);
        Integer acceptCount = projectService.findAcceptFileCount(id);
        modelAndView.addObject("taskCount", taskCount);
        modelAndView.addObject("signCount", signCount);
        modelAndView.addObject("contractCount", contractCount);
        modelAndView.addObject("acceptCount", acceptCount);
        return modelAndView;
    }

    /**
     * 单项目列表
     */
    @GetMapping("/single/message/{id}/{receiverId}")
    public ModelAndView single(@PathVariable("id") Integer id, @PathVariable("receiverId") Integer receiverId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("project/single");
        projectService.updateReceiverStatus(receiverId);
        Project p = projectService.findProjectById(id);
        modelAndView.addObject("project", p);
        Integer taskCount = projectService.findTaskFileCount(id);
        Integer signCount = projectService.findSignFileCount(id);
        Integer contractCount = projectService.findContractFileCount(id);
        Integer acceptCount = projectService.findAcceptFileCount(id);
        modelAndView.addObject("taskCount", taskCount);
        modelAndView.addObject("signCount", signCount);
        modelAndView.addObject("contractCount", contractCount);
        modelAndView.addObject("acceptCount", acceptCount);
        return modelAndView;
    }

    /**
     * 编辑生产任务进度
     */
    @ResponseBody
    @PostMapping("/task/produce/edit")
    public BaseResponse editProduceTask(ProduceTask produceTask, HttpServletRequest request) {
        if (produceTask.getComment() != null) {
            produceTask.setComment(produceTask.getComment().replaceAll("\r\n|\r|\n|\n\r",""));
        }
        ProduceTask t = taskService.saveProduceTask(produceTask);
//        logService.saveLog("编辑生产任务进度：【项目名：" + t.getProject().getName() + " 项目号：" + t.getProject().getCode() + "   任务名：" + t.getProduceName() + " 】", request);
        return BaseResponse.toResponse(t.getId());
    }

    /**
     * 编辑科室任务进度
     */
    @ResponseBody
    @PostMapping("/task/dept/edit")
    public BaseResponse editDeptTask(DeptTask deptTask, HttpServletRequest request) {
        if (deptTask.getComment() != null) {
            deptTask.setComment(deptTask.getComment().replaceAll("\r\n|\r|\n|\n\r",""));
        }
        DeptTask t = taskService.saveDeptTask(deptTask);
//        logService.saveLog("编辑设计任务进度：【项目名：" + t.getProject().getName() + "   项目号：" + t.getProject().getCode() + " "+ t.getName() + "   任务名：" + t.getStepName() + " 】", request);
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
        BaseDataTableModel<DeptTaskDto> dto = taskService.findTasksByCriteria(query);
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
        BaseDataTableModel<DeptTaskDto> dto = taskService.findProcessList(query);
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
        BaseDataTableModel<DeptTaskDto> dto = taskService.findProduceList(query);
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
    public BaseResponse upload(@RequestParam("file") MultipartFile[] file,
                               @RequestParam("id") Integer id,
                               @RequestParam("code") String code,
                               @RequestParam("type") Integer type,
                               HttpServletRequest request) {

        if (null != file && file.length > 0) {
            //遍历并保存文件
            for (MultipartFile f : file) {
                if (!f.isEmpty()) {
                    //取得当前上传文件的文件名称
                    String fileName = f.getOriginalFilename();
                    String path = uploadPath + code + "/" + type + "/";
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
                        projectService.saveImage(id, fileName, path + fileName, type);
                    } catch (Exception e) {
                        return BaseResponse.STATUS_400;
                    }
                }
            }
        } else {
            return BaseResponse.STATUS_400;
        }
        Project project = projectService.findProjectById(id);
//        logService.saveLog("上传文件：数量" + file.length + " 【项目名：" + project.getName() + "  项目号：" + project.getCode() + "】", request);
        return BaseResponse.STATUS_200;
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response, @RequestParam("ids") Integer[] ids, @RequestParam("code")String code, HttpServletRequest request) {
        projectService.download(response, ids, code);
//        logService.saveLog("下载文件：数量" + ids.length + "  【项目号：" + code + "】", request);
    }

    @ResponseBody
    @PostMapping("/delFile")
    public BaseResponse delFile(@RequestParam("ids") Integer[] ids, HttpServletRequest request) {
        projectService.delFile(ids);
//        logService.saveLog("删除文件", request);
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
