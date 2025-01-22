package com.ai_offshore.tools.web.wbs.project.mapper;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.ai_offshore.tools.web.wbs.project.mapper.model.Project;

@Mapper
public interface ProjectMapper {
    
    /**
     * 案件を登録
     */
    void insert(Project project);
    
    /**
     * チケット番号で案件を取得
     */
    Optional<Project> selectByTicketNumber(String ticketNumber);
    
    /**
     * 全案件を取得
     */
    List<Project> selectAll();
    
    /**
     * 案件を更新
     */
    void update(Project project);
    
    /**
     * 案件を論理削除
     */
    void delete(String ticketNumber);
    
    /**
     * チケット番号の重複チェック
     */
    boolean existsByTicketNumber(String ticketNumber);
    
    /**
     * 案件名で検索
     */
    List<Project> selectByProjectName(@Param("projectName") String projectName);
} 