package com.ai_offshore.tools.wbs.web.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_offshore.tools.wbs.web.common.exception.BusinessException;
import com.ai_offshore.tools.wbs.web.project.mapper.ProjectMapper;
import com.ai_offshore.tools.wbs.web.project.mapper.model.Project;

@Service
public class ProjectService {

    private final ProjectMapper projectMapper;

    public ProjectService(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    /**
     * 案件を登録
     */
    @Transactional
    public void create(Project project) {
        if (existsByTicketNumber(project.getTicketNumber())) {
            throw new BusinessException(
                "チケット番号 " + project.getTicketNumber() + " は既に登録されています",
                project,
                "/project/regist"
            );
        }
        projectMapper.insert(project);
    }

    /**
     * チケット番号で案件を取得
     */
    public Project findByTicketNumber(String ticketNumber) {
        return projectMapper.selectByTicketNumber(ticketNumber)
            .orElse(null);
    }

    /**
     * 全案件を取得
     */
    public List<Project> findByProjectName(String projectName) {
        return projectMapper.selectByProjectName(projectName);
    }

    /**
     * 案件を更新
     */
    @Transactional
    public void update(Project project) {
        // 既存の案件を取得
        Project existingProject = projectMapper.selectByTicketNumber(project.getTicketNumber())
            .orElseThrow(() -> new RuntimeException("案件が見つかりません"));
        
        // 更新対象のフィールドを設定
        existingProject.setProjectName(project.getProjectName());
        existingProject.setServiceKbnCode(project.getServiceKbnCode());
        existingProject.setStatus(project.getStatus());
        existingProject.setPriority(project.getPriority());
        existingProject.setProgressRate(project.getProgressRate());
        existingProject.setDescription(project.getDescription());
        
        // 更新を実行
        projectMapper.update(existingProject);
    }

    /**
     * 案件を論理削除
     */
    @Transactional
    public void delete(String ticketNumber) {
        projectMapper.delete(ticketNumber);
    }

    /**
     * チケット番号の重複チェック
     * 
     * @param ticketNumber チケット番号
     * @return 存在する場合はtrue
     */
    public boolean existsByTicketNumber(String ticketNumber) {
        return projectMapper.existsByTicketNumber(ticketNumber);
    }
} 