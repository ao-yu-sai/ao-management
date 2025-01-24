package com.ai_offshore.tools.wbs.web.task_manage.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai_offshore.tools.wbs.web.master.category.mapper.model.Category;
import com.ai_offshore.tools.wbs.web.task_manage.mapper.model.ProjectFunction;
import com.ai_offshore.tools.wbs.web.task_manage.mapper.model.ProjectFunctionTaskInfo;
import com.ai_offshore.tools.wbs.web.task_manage.mapper.model.TaskManage;
import com.ai_offshore.tools.wbs.web.task_manage.service.TaskManageService;

@Controller
@RequestMapping("/task-manage")
public class TaskManageController {

    private final TaskManageService taskService;

    public TaskManageController(TaskManageService taskService) {
        this.taskService = taskService;
    }

    /**
     * タスク一覧画面を表示
     */
    @GetMapping("/list")
    public String showList(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("tasks", taskService.findByKeyword(keyword));
        model.addAttribute("keyword", keyword);
        return "task_manage/list";
    }

    /**
     * チケット詳細を取得
     */
    @GetMapping("/ticket/{ticketNumber}")
    @ResponseBody
    public TaskManage getTicketDetail(@PathVariable String ticketNumber) {
        return taskService.findByTicketNumber(ticketNumber);
    }

    /**
     * 機能一覧を取得
     */
    @GetMapping("/functions")
    @ResponseBody
    public List<ProjectFunction> getFunctions(
        @RequestParam String ticketNumber,
        @RequestParam String serviceKbnCode) {
        return taskService.findFunctionsByTicketAndService(ticketNumber, serviceKbnCode);
    }

    /**
     * サービス区分に紐づく利用可能な機能一覧を取得
     */
    @GetMapping("/available-functions")
    @ResponseBody
    public List<ProjectFunction> getAvailableFunctions(
        @RequestParam String serviceKbnCode,
        @RequestParam String ticketNumber,
        @RequestParam(required = false) String keyword) {
        return taskService.findAvailableFunctions(serviceKbnCode, ticketNumber, keyword);
    }

    /**
     * 機能を追加
     */
    @PostMapping("/functions/add")
    @ResponseBody
    public void addFunctions(@RequestBody AddFunctionsRequest request) {
        taskService.addFunctions(
            request.getTicketNumber(),
            request.getServiceKbnCode(),
            request.getFunctionCodes()
        );
    }

    /**
     * 案件機能別タスク情報を取得
     */
    @GetMapping("/function-task-info")
    @ResponseBody
    public List<ProjectFunctionTaskInfo> getProjectFunctionTaskInfo(
        @RequestParam String serviceKbnCode,
        @RequestParam String ticketNumber,
        @RequestParam String functionCode) {
        return taskService.findProjectFunctionTaskInfo(serviceKbnCode, ticketNumber, functionCode);
    }

    /**
     * タスクを追加
     */
    @PostMapping("/tasks/add")
    @ResponseBody
    public void addTask(@RequestBody ProjectFunctionTaskInfo taskInfo) {
        taskService.addTask(taskInfo);
    }

    /**
     * タスク区分を取得
     */
    @GetMapping("/task-categories")
    @ResponseBody
    public List<Category> getTaskCategories() {
        return taskService.findTaskCategories();
    }
}

/**
 * 機能追加リクエスト
 */
class AddFunctionsRequest {
    private String ticketNumber;
    private String serviceKbnCode;
    private List<String> functionCodes;
    
    // Getters and Setters
    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }
    public String getServiceKbnCode() { return serviceKbnCode; }
    public void setServiceKbnCode(String serviceKbnCode) { this.serviceKbnCode = serviceKbnCode; }
    public List<String> getFunctionCodes() { return functionCodes; }
    public void setFunctionCodes(List<String> functionCodes) { this.functionCodes = functionCodes; }
} 