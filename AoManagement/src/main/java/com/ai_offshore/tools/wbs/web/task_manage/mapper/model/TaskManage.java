package com.ai_offshore.tools.wbs.web.task_manage.mapper.model;

import lombok.Data;

@Data
public class TaskManage {
    private String ticketNumber;
    private String projectName;
    private String serviceKbnCode;
    private String status;
    private String statusName;
    private String priority;
    private String priorityName;
    private String description;
} 