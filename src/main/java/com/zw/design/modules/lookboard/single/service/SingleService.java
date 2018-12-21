package com.zw.design.modules.lookboard.single.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.baseinfosetting.sectiontype.entity.SectionType;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.lookboard.single.entity.Message;
import com.zw.design.modules.build.distributedesigntask.entity.Task;
import com.zw.design.modules.lookboard.single.query.SingleQuery;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface SingleService {

    // 按项目ID查询项目
    Project findProjectById(Integer id);

    // 按code查询项目
    Project findProjectByCode(String code);

    // 查询科室类型
    List<SectionType> findSectionTypeByStatus(Integer status);

    // 修改项目状态
    Project updateStatus(Integer id, Integer status, String comment);

    // 项目任务列表
    BaseDataTableModel<Task> findTaskByQuery(SingleQuery query);

    // 修改任务状态
    Task updateTaskStatus(Integer id, Integer status);

    // 修改项目
    Project updateProject(Project project, Integer[] sectionId);

    // 编辑任务
    Task editTask(Task task);

    // 撤消任务
    Task cancelTask(Integer id);

    // 发送消息
    Message sendMessage(Message message, Integer projectId, List<String> users);

    // 修改未读消息状态
    void updateReceiverStatus(Integer receiverId);

    // 按ID查询任务
    Task findTaskById(Integer taskId);

    // 保存文件
    void saveImage(Integer projectId, String fileName, String s, Task task);

    // 下载文件
    void download(HttpServletResponse response, Integer[] ids, String code);

    // 删除文件
    void delFile(Integer[] ids);


}
