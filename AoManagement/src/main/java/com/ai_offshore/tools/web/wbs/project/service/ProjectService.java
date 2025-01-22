package com.ai_offshore.tools.web.wbs.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_offshore.tools.web.wbs.project.mapper.ProjectMapper;
import com.ai_offshore.tools.web.wbs.project.mapper.model.Project;
import com.ai_offshore.tools.web.wbs.common.exception.BusinessException;

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
        return projectMapper.selectByTicketNumber(ticketNumber);
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
        projectMapper.update(project);
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