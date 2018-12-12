package com.zw.design.modules.build.distributedesigntask.service;

import com.zw.design.modules.build.distributedesigntask.entity.ProduceTask;
import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.build.distributedesigntask.mapper.DeptTaskDao;
import com.zw.design.modules.build.distributedesigntask.repository.ProduceTaskRepository;
import com.zw.design.modules.build.create.repository.ProjectRepository;
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
//        int e = deptTaskDao.updateProcess5();

        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {

                ProduceTask task = new ProduceTask();
                task.setProduceName(Const.PRODUCE_TASK_STEP_NAME[8]);
                task.setProduceNum(9);
                task.setProject(project);
                produceTaskRepository.save(task);

        }

    }
}
