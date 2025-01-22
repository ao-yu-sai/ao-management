package com.ai_offshore.tools.web.wbs.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

/**
 * Redmineのチケット情報を格納するクラス
 */
@Data
public class RedmineIssue {
    
    /** チケット情報 */
    @JsonProperty("issue")
    private Issue issue;

    /**
     * チケットの詳細情報を格納する内部クラス
     */
    @Data
    public static class Issue {
        /** チケットID */
        private Integer id;
        /** チケットの件名 */
        private String subject;
        /** チケットの説明 */
        private String description;
        /** チケットのステータス */
        private Status status;
        /** チケットの優先度 */
        private Priority priority;
        /** チケットのプロジェクト */
        private Project project;
        /** チケットのトラッカー */
        private Tracker tracker;
        /** チケットの作成者 */
        private User author;
        
        /** チケットの割り当て先 */
        @JsonProperty("assigned_to")
        private User assignedTo;
        /** チケットの親チケット */
        private Issue parent;
        
        /** チケットの開始日 */
        @JsonProperty("start_date")
        private String startDate;
        
        /** チケットの期限 */
        @JsonProperty("due_date")
        private String dueDate;
        
        /** チケットの完了率 */
        @JsonProperty("done_ratio")
        private Integer doneRatio;
        
        /** チケットのプライベート設定 */
        @JsonProperty("is_private")
        private Boolean isPrivate;
        
        /** チケットの見積もり時間 */
        @JsonProperty("estimated_hours")
        private Double estimatedHours;
        
        /** チケットの総見積もり時間 */
        @JsonProperty("total_estimated_hours")
        private Double totalEstimatedHours;
        
        /** チケットの実績時間 */
        @JsonProperty("spent_hours")
        private Double spentHours;
        
        /** チケットの総実績時間 */
        @JsonProperty("total_spent_hours")
        private Double totalSpentHours;
        
        /** チケットのカスタムフィールド */
        @JsonProperty("custom_fields")
        private List<CustomField> customFields;
        
        /** チケットの作成日時 */
        @JsonProperty("created_on")
        private String createdOn;
        
        /** チケットの更新日時 */
        @JsonProperty("updated_on")
        private String updatedOn;
        
        /** チケットの完了日時 */
        @JsonProperty("closed_on")
        private String closedOn;
    }

    /**
     * チケットのステータス情報を格納する内部クラス
     */
    @Data
    public static class Status {
        /** ステータスID */
        private Integer id;
        /** ステータス名 */
        private String name;
    }

    /**
     * チケットの優先度情報を格納する内部クラス
     */
    @Data
    public static class Priority {
        /** 優先度ID */
        private Integer id;
        /** 優先度名 */
        private String name;
    }

    /**
     * チケットのプロジェクト情報を格納する内部クラス
     */
    @Data
    public static class Project {
        /** プロジェクトID */
        private Integer id;
        /** プロジェクト名 */
        private String name;
    }
    
    /**
     * チケットのトラッカー情報を格納する内部クラス
     */
    @Data
    public static class Tracker {
        /** トラッカーID */
        private Integer id;
        /** トラッカー名 */
        private String name;
    }
    
    /**
     * チケットの作成者情報を格納する内部クラス
     */
    @Data
    public static class User {
        /** 作成者ID */
        private Integer id;
        /** 作成者名 */
        private String name;
    }
    
    /**
     * チケットのカスタムフィールド情報を格納する内部クラス
     */
    @Data
    public static class CustomField {
        /** カスタムフィールドID */
        private Integer id;
        /** カスタムフィールド名 */
        private String name;
        /** カスタムフィールドの複数値設定 */
        private Boolean multiple;
        
        /** カスタムフィールドの値 */
        @JsonProperty("value")
        private Object value;
    }
} 