package com.ai_offshore.tools.wbs.web.master.task.mapper.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Task {
    private Long taskId;
    private String ticketNumber;
    private String serviceKbnCode;
    private String functionCode;
    private String taskKbnCode;
    private String taskName;
    private String personInCharge;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private Double plannedManHours;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private Double actualManHours;
    private Integer progressRate;
}