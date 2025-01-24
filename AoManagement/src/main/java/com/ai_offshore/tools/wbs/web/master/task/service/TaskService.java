package com.ai_offshore.tools.wbs.web.master.task.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_offshore.tools.wbs.web.master.task.mapper.TaskMapper;
import com.ai_offshore.tools.wbs.web.master.task.mapper.model.Task;

@Service
public class TaskService {

    private final TaskMapper taskMapper;

    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Transactional
    public void deleteTask(String serviceKbnCode, String ticketNumber,
            String functionCode, String taskKbnCode, String personInCharge) {
        Task task = taskMapper.findByCompositeKey(serviceKbnCode, ticketNumber,
                functionCode, taskKbnCode, personInCharge);
        if (task == null) {
            throw new IllegalArgumentException("指定されたタスクが見つかりません");
        }
        taskMapper.deleteByCompositeKey(serviceKbnCode, ticketNumber,
                functionCode, taskKbnCode, personInCharge);
    }
}