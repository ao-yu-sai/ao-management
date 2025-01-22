package com.ai_offshore.tools.web.wbs.project.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ai_offshore.tools.web.wbs.common.helper.RedmineHelper;
import com.ai_offshore.tools.web.wbs.common.model.RedmineIssue;
import com.ai_offshore.tools.web.wbs.master.category.mapper.model.Category;
import com.ai_offshore.tools.web.wbs.master.category.service.CategoryService;
import com.ai_offshore.tools.web.wbs.project.mapper.model.Project;
import com.ai_offshore.tools.web.wbs.project.service.ProjectService;

/**
 * 案件管理のコントローラークラス
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;
    private final CategoryService categoryService;
    private final RedmineHelper redmineHelper;

    public ProjectController(ProjectService projectService, CategoryService categoryService, RedmineHelper redmineHelper) {
        this.projectService = projectService;
        this.categoryService = categoryService;
        this.redmineHelper = redmineHelper;
    }

    /**
     * 案件登録画面を表示
     */
    @GetMapping("/regist")
    public String showRegistForm(Model model) {
        // サービス区分の選択肢を取得
        List<Category> serviceCategories = categoryService.findByCategoryTypeCode("SERVICE_KBN");
        
        if (!model.containsAttribute("project")) {
            Project project = new Project();
            // デフォルトで最初のサービス区分を選択
            if (!serviceCategories.isEmpty()) {
                project.setServiceKbnCode(serviceCategories.get(0).getCategoryCode());
            }
            model.addAttribute("project", project);
        }
        model.addAttribute("serviceCategories", serviceCategories);
        return "project/regist";
    }

    /**
     * 案件を登録
     */
    @PostMapping("/regist")
    public String register(@ModelAttribute Project project, RedirectAttributes redirectAttributes) {
        try {
            projectService.create(project);
            redirectAttributes.addFlashAttribute("successMessage", "案件を登録しました");
            return "redirect:/project/list";
        } catch (Exception e) {
            log.error("案件登録に失敗しました", e);
            redirectAttributes.addFlashAttribute("errorMessage", "案件登録に失敗しました");
            redirectAttributes.addFlashAttribute("project", project);
            return "redirect:/project/regist";
        }
    }

    /**
     * チケット情報を取得
     */
    @GetMapping("/ticket/{ticketNumber}")
    @ResponseBody
    public ResponseEntity<RedmineIssue> getTicketInfo(@PathVariable String ticketNumber) {
        try {
            RedmineIssue issue = redmineHelper.getIssue(ticketNumber);
            return ResponseEntity.ok(issue);
        } catch (Exception e) {
            log.error("チケット情報の取得に失敗しました: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
} 