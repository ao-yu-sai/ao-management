package com.ai_offshore.tools.wbs.web.task_manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ai_offshore.tools.wbs.web.master.category.mapper.model.Category;
import com.ai_offshore.tools.wbs.web.task_manage.mapper.model.ProjectFunction;
import com.ai_offshore.tools.wbs.web.task_manage.mapper.model.ProjectFunctionTaskInfo;
import com.ai_offshore.tools.wbs.web.task_manage.mapper.model.TaskManage;

@Mapper
public interface TaskManageMapper {
    
    /**
     * キーワードでタスクを検索
     */
    List<TaskManage> selectByKeyword(@Param("keyword") String keyword);

    /**
     * チケット番号とサービス区分コードに紐づく機能一覧を取得
     */
    List<ProjectFunction> selectFunctionsByTicketAndService(
        @Param("ticketNumber") String ticketNumber,
        @Param("serviceKbnCode") String serviceKbnCode
    );

    /**
     * チケット番号でタスクを取得
     */
    TaskManage selectByTicketNumber(@Param("ticketNumber") String ticketNumber);

    /**
     * 選択された案件のサービス区分コードに基づいて機能情報マスターから機能一覧を取得
     */
    List<ProjectFunction> selectAvailableFunctions(
        @Param("serviceKbnCode") String serviceKbnCode,
        @Param("ticketNumber") String ticketNumber,
        @Param("keyword") String keyword
    );

    /**
     * 案件機能情報を登録
     */
    void insertProjectFunction(
        @Param("ticketNumber") String ticketNumber,
        @Param("serviceKbnCode") String serviceKbnCode,
        @Param("functionCode") String functionCode
    );

    /**
     * 案件機能別タスク情報を取得
     */
    List<ProjectFunctionTaskInfo> selectProjectFunctionTaskInfo(
        @Param("serviceKbnCode") String serviceKbnCode,
        @Param("ticketNumber") String ticketNumber,
        @Param("functionCode") String functionCode
    );

    void insertTask(ProjectFunctionTaskInfo taskInfo);

    List<Category> selectTaskCategories();
} 