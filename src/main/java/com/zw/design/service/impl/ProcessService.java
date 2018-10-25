package com.zw.design.service.impl;

import com.zw.design.entity.ProduceTask;
import com.zw.design.entity.Project;
import com.zw.design.mapper.DeptTaskDao;
import com.zw.design.repository.ProduceTaskRepository;
import com.zw.design.repository.ProjectRepository;
import com.zw.design.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProduceTaskRepository produceTaskRepository;
    @Autowired
    DeptTaskDao deptTaskDao;

    public void process() {

        int a = deptTaskDao.updateProcess1();
        int b = deptTaskDao.updateProcess2();
        int c = deptTaskDao.updateProcess3();
        int d = deptTaskDao.updateProcess4();
        int e = deptTaskDao.updateProcess5();

        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            for (int i = 2; i < 9; i++) {
                if (i == 3 || i == 6) {
                    continue;
                }
                ProduceTask task = new ProduceTask();
                task.setProduceName(Const.PRODUCE_TASK_STEP_NAME[i]);
                task.setProduceNum(i + 1);
                task.setProject(project);
                produceTaskRepository.save(task);
            }
        }

    }
}
