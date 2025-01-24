package com.ai_offshore.tools.wbs.web.project.mapper.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Project {
    private String ticketNumber;      // チケット番号
    private String projectName;       // 案件名
    private String description;       // 説明
    private String serviceKbnCode;    // サービス区分コード
    private String serviceKbnName;    // サービス区分名
    private String status;           // ステータス
    private String statusName; // ステータス
    private String priority;         // 優先度
    private String priorityName; // 優先度
    private Integer progressRate;    // 進捗率
    private Boolean isActive;        // 有効フラグ
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
} 