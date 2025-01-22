package com.ai_offshore.tools.web.wbs.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_offshore.tools.web.wbs.project.mapper.ProjectMapper;
import com.ai_offshore.tools.web.wbs.project.mapper.model.Project;

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
        if (projectMapper.existsByTicketNumber(project.getTicketNumber())) {
            throw new IllegalArgumentException("チケット番号が重複しています");
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
    public List<Project> findAll() {
        return projectMapper.selectAll();
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
} 