package com.ai_offshore.tools.wbs.web.task_manage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_offshore.tools.wbs.web.master.category.mapper.model.Category;
import com.ai_offshore.tools.wbs.web.task_manage.mapper.TaskManageMapper;
import com.ai_offshore.tools.wbs.web.task_manage.mapper.model.ProjectFunction;
import com.ai_offshore.tools.wbs.web.task_manage.mapper.model.ProjectFunctionTaskInfo;
import com.ai_offshore.tools.wbs.web.task_manage.mapper.model.TaskManage;

@Service
public class TaskManageService {

    private final TaskManageMapper taskMapper;

    public TaskManageService(TaskManageMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    /**
     * キーワードでタスクを検索
     */
    public List<TaskManage> findByKeyword(String keyword) {
        return taskMapper.selectByKeyword(keyword);
    }

    /**
     * チケット番号でタスクを取得
     */
    public TaskManage findByTicketNumber(String ticketNumber) {
        return taskMapper.selectByTicketNumber(ticketNumber);
    }

    /**
     * チケット番号とサービス区分コードに紐づく機能一覧を取得
     */
    public List<ProjectFunction> findFunctionsByTicketAndService(String ticketNumber, String serviceKbnCode) {
        return taskMapper.selectFunctionsByTicketAndService(ticketNumber, serviceKbnCode);
    }

    /**
     * サービス区分に紐づく利用可能な機能一覧を取得
     */
    public List<ProjectFunction> findAvailableFunctions(String serviceKbnCode, String ticketNumber, String keyword) {
        return taskMapper.selectAvailableFunctions(serviceKbnCode, ticketNumber, keyword);
    }

    /**
     * 機能を追加
     */
    @Transactional
    public void addFunctions(String ticketNumber, String serviceKbnCode, List<String> functionCodes) {
        for (String functionCode : functionCodes) {
            taskMapper.insertProjectFunction(ticketNumber, serviceKbnCode, functionCode);
        }
    }

    /**
     * 案件機能別タスク情報を取得
     */
    public List<ProjectFunctionTaskInfo> findProjectFunctionTaskInfo(String serviceKbnCode, String ticketNumber, String functionCode) {
        return taskMapper.selectProjectFunctionTaskInfo(serviceKbnCode, ticketNumber, functionCode);
    }

    @Transactional
    public void addTask(ProjectFunctionTaskInfo taskInfo) {
        taskMapper.insertTask(taskInfo);
    }

    public List<Category> findTaskCategories() {
        return taskMapper.selectTaskCategories();
    }
} 