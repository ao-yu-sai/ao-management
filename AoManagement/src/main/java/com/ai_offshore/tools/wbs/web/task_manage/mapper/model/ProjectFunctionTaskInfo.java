package com.ai_offshore.tools.wbs.web.task_manage.mapper.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ProjectFunctionTaskInfo {
    private int projectFunctionTaskId;
    private String serviceKbnCode;
    private String ticketNumber;
    private String functionCode;
    private List<String> taskKbnCodes;
    private String taskKbnCode;
    private String taskName;
    private String personInCharge;
    private String plannedStartDate;
    private String plannedEndDate;
    private double plannedManHours;
    private String actualStartDate;
    private String actualEndDate;
    private double actualManHours;
    private int progressRate;
    private Date createdAt;
    private Date updatedAt;
} 