package com.ai_offshore.tools.wbs.web.task_manage.mapper.model;

import lombok.Data;
import java.util.Date;

@Data
public class ProjectFunctionTaskInfo {
    private int projectFunctionTaskId;
    private String serviceKbnCode;
    private String ticketNumber;
    private String functionCode;
    private String taskKbnCode;
    private String personInCharge;
    private Date plannedStartDate;
    private Date plannedEndDate;
    private double plannedManHours;
    private Date actualStartDate;
    private Date actualEndDate;
    private double actualManHours;
    private int progressRate;
    private Date createdAt;
    private Date updatedAt;
} 