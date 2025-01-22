package com.ai_offshore.tools.web.wbs.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
        
        /** チケットの作成日時 */
        @JsonProperty("created_on")
        private String createdOn;
        
        /** チケットの更新日時 */
        @JsonProperty("updated_on")
        private String updatedOn;
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
} 