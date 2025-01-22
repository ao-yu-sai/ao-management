package com.ai_offshore.tools.web.wbs.master.task.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai_offshore.tools.web.wbs.master.task.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory
            .getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTask(
            @RequestParam String serviceKbnCode,
            @RequestParam String ticketNumber,
            @RequestParam String functionCode, @RequestParam String taskKbnCode,
            @RequestParam String personInCharge) {
        try {
            taskService.deleteTask(serviceKbnCode, ticketNumber, functionCode,
                    taskKbnCode, personInCharge);
            return ResponseEntity.ok("タスクを削除しました");
        } catch (IllegalArgumentException e) {
            logger.warn("Task not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error deleting task: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("タスクの削除に失敗しました: " + e.getMessage());
        }
    }
}