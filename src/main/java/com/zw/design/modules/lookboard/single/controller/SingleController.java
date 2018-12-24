package com.zw.design.modules.lookboard.single.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.distributedesigntask.query.DistributeDesignTaskQuery;
import com.zw.design.modules.lookboard.single.entity.Message;
import com.zw.design.modules.build.distributedesigntask.entity.Task;
import com.zw.design.modules.lookboard.single.entity.TaskEmployee;
import com.zw.design.modules.lookboard.single.query.SingleQuery;
import com.zw.design.modules.lookboard.single.service.SingleService;
import com.zw.design.modules.system.user.entity.SysUser;
import com.zw.design.modules.system.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@Slf4j
@RequestMapping("/board/single")
public class SingleController {

    private String prefix = "lookboard/single";

    @Autowired
    private SingleService singleService;
    @Autowired
    private UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    // 单项目页面
    @GetMapping("/{id}")
    @RequiresAuthentication
    public String single(@PathVariable("id") Integer id, Model model) {
        Project p = singleService.findProjectById(id);
        model.addAttribute("project", p);
        return prefix + "/detail";
    }

    // 单项目页面
    @GetMapping("/{id}/{receiverId}")
    @RequiresAuthentication
    public String single(@PathVariable("id") Integer id, @PathVariable("receiverId") Integer receiverId, Model model) {
        singleService.updateReceiverStatus(receiverId);
        Project p = singleService.findProjectById(id);
        model.addAttribute("project", p);
        return prefix + "/detail";
    }

    // 修改项目页面
    @GetMapping("/update/{id}")
    @RequiresPermissions({"single:update"})
    public String update(@PathVariable("id") Integer id, Model model) {
        Project project = singleService.findProjectById(id);
        findSectionId(model, project);
        model.addAttribute("sectionType", singleService.findSectionTypeByStatus(1));
        model.addAttribute("project", project);
        return prefix + "/update";
    }

    // 修改项目页面
    @GetMapping("/update/code")
    @RequiresPermissions({"single:update"})
    public String updateProject(@RequestParam("codeQuery") String code, Model model) {
        Project project = singleService.findProjectByCode(code);
        if (project != null) {
            findSectionId(model, project);
            model.addAttribute("sectionType", singleService.findSectionTypeByStatus(1));
            model.addAttribute("project", project);
        }
        return prefix + "/update";
    }

    private void findSectionId(Model model, Project project) {
        for (Task task : project.getTasks()) {
            if (task.getSection() != null) {
                if (task.getStatus() > 0 && task.getSection().getSectionType().getId() == 1) {
                    model.addAttribute("first", task.getSection().getId());
                }
                if (task.getStatus() > 0 && task.getSection().getSectionType().getId() == 2) {
                    model.addAttribute("second", task.getSection().getId());
                }
                if (task.getStatus() > 0 && task.getSection().getSectionType().getId() == 3) {
                    model.addAttribute("third", task.getSection().getId());
                }
                if (task.getStatus() > 0 && task.getSection().getSectionType().getId() == 4) {
                    model.addAttribute("fourth", task.getSection().getId());
                }
            }
        }
    }

    // 任务列表
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"single:update"})
    public BaseResponse ddtList(SingleQuery query) {
        BaseDataTableModel<Task> tasks = singleService.findTaskByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(tasks);
        return baseResponse;
    }

    // 修改任务状态
    @ResponseBody
    @PostMapping("/updateTaskStatus")
    @RequiresPermissions({"single:update"})
    public BaseResponse updateTaskStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status) {
        Task task = singleService.updateTaskStatus(id, status);
        return BaseResponse.toResponse(task);
    }

    // 修改项目
    @ResponseBody
    @PostMapping("/update")
    @RequiresPermissions({"single:update"})
    public BaseResponse update(Project project, @RequestParam("sectionId") Integer[] sectionId) {
        Project p = singleService.updateProject(project,sectionId);
        return BaseResponse.toResponse(p);
    }

    // 修改项目状态
    @ResponseBody
    @PostMapping("/status")
    @RequiresPermissions(value = {"single:cancel","single:regain"},logical = Logical.OR)
    public BaseResponse updateStatus(@RequestParam("id")Integer id, @RequestParam("status") Integer status, @RequestParam("comment") String comment, HttpServletRequest request) {
        Project project = singleService.updateStatus(id, status, comment);
        return BaseResponse.toResponse(project);
    }

    // 编辑任务进度
    @ResponseBody
    @PostMapping("/editTask")
    @RequiresPermissions(value = {"single:sign:edit", "single:contract:edit", "single:dept:edit", "single:process:edit", "single:produce:edit","single:debug:edit","single:accept:edit","single:save:edit"},logical = Logical.OR)
    public BaseResponse editTask(Task task) {
        if (task.getComment() != null) {
            task.setComment(task.getComment().replaceAll("\r\n|\r|\n|\n\r",""));
        }
        Task t = singleService.editTask(task);
        return BaseResponse.toResponse(t);
    }

    // 负责人列表
    @ResponseBody
    @PostMapping("/editTask/employee/list")
    @RequiresPermissions(value = {"single:sign:edit", "single:contract:edit", "single:dept:edit", "single:process:edit", "single:produce:edit","single:debug:edit","single:accept:edit","single:save:edit"},logical = Logical.OR)
    public BaseResponse empList(SingleQuery singleQuery) {
        BaseDataTableModel<TaskEmployee> tasks = singleService.findTaskEmployeeByTaskId(singleQuery);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(tasks);
        return baseResponse;
    }

    // 添加负责人
    @ResponseBody
    @PostMapping("/editTask/employee/save")
    @RequiresPermissions(value = {"single:sign:edit", "single:contract:edit", "single:dept:edit", "single:process:edit", "single:produce:edit","single:debug:edit","single:accept:edit","single:save:edit"},logical = Logical.OR)
    public BaseResponse empSave(TaskEmployee taskEmployee) {
        return BaseResponse.toResponse(singleService.saveEmployee(taskEmployee));
    }

    // 修改负责人
    @ResponseBody
    @PostMapping("/editTask/employee/update")
    @RequiresPermissions(value = {"single:sign:edit", "single:contract:edit", "single:dept:edit", "single:process:edit", "single:produce:edit","single:debug:edit","single:accept:edit","single:save:edit"},logical = Logical.OR)
    public BaseResponse empUpdate(TaskEmployee taskEmployee) {
        return BaseResponse.toResponse(singleService.updateEmployee(taskEmployee));
    }

    // 删除负责人
    @ResponseBody
    @PostMapping("/editTask/employee/status")
    @RequiresPermissions(value = {"single:sign:edit", "single:contract:edit", "single:dept:edit", "single:process:edit", "single:produce:edit","single:debug:edit","single:accept:edit","single:save:edit"},logical = Logical.OR)
    public BaseResponse updateEmpLoyeeStatus(@RequestParam("id")Integer id) {
        return BaseResponse.toResponse(singleService.updateEmployeeStatus(id, 0));
    }

    // 撤消任务进度
    @ResponseBody
    @PostMapping("/cancelTask")
    @RequiresPermissions(value = {"single:sign:cancel", "single:contract:cancel", "single:dept:cancel", "single:process:cancel", "single:produce:cancel","single:debug:cancel","single:accept:cancel","single:save:cancel"},logical = Logical.OR)
    public BaseResponse cancelTask(@RequestParam("id")Integer id) {
        Task task = singleService.cancelTask(id);
        return BaseResponse.toResponse(task);
    }

    // 发消息
    @ResponseBody
    @PostMapping("/sendMessage")
    @RequiresPermissions({"single:sendMessage"})
    public BaseResponse sendMessage(Message message, Integer projectId) {
        // 获取@用户名集合
        List<String> users = new ArrayList<>();
        Pattern pattern = Pattern.compile("@(\\S*)\\s+?");

        Matcher matcher = pattern.matcher(message.getContent());
        while (matcher.find()) {
            users.add(matcher.group(1));
        }

        Message msg = singleService.sendMessage(message, projectId, users);
        return BaseResponse.toResponse(msg);
    }

    // 所有用户
    @ResponseBody
    @GetMapping("/users")
    @RequiresAuthentication
    public BaseResponse getAll() {
        List<SysUser> users = userService.findAllByStatus();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(users);
        return baseResponse;
    }

    // 上传文件
    @ResponseBody
    @PostMapping("/upload")
    @RequiresPermissions(value = {"single:dt:upload", "single:sign:upload", "single:contract:upload", "single:accept:upload"},logical = Logical.OR)
    public BaseResponse upload(@RequestParam("file") MultipartFile[] file, @RequestParam("projectId") Integer projectId, @RequestParam("code") String code, @RequestParam("taskId") Integer taskId) {
        if (null != file && file.length > 0) {
            Task task = singleService.findTaskById(taskId);
            //遍历并保存文件
            for (MultipartFile f : file) {
                if (!f.isEmpty()) {
                    //取得当前上传文件的文件名称
                    String fileName = f.getOriginalFilename();
                    String path = uploadPath + code + "/" + task.getTaskName().getId() + "/";
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
                        singleService.saveImage(projectId, fileName, path + fileName, task);
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

    // 下载文件
    @GetMapping("/download")
    @RequiresPermissions(value = {"single:dt:download", "single:sign:download", "single:contract:download", "single:accept:download"},logical = Logical.OR)
    public void download(HttpServletResponse response, @RequestParam("ids") Integer[] ids, @RequestParam("code")String code) {
        singleService.download(response, ids, code);
    }

    // 删除文件
    @ResponseBody
    @PostMapping("/delFile")
    @RequiresPermissions(value = {"single:dt:delFile", "single:sign:delFile", "single:contract:delFile", "single:accept:delFile"},logical = Logical.OR)
    public BaseResponse delFile(@RequestParam("ids") Integer[] ids, HttpServletRequest request) {
        singleService.delFile(ids);
        return BaseResponse.STATUS_200;
    }
}
