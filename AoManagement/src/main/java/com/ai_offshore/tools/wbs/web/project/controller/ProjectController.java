package com.ai_offshore.tools.wbs.web.project.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ai_offshore.tools.wbs.web.common.helper.RedmineHelper;
import com.ai_offshore.tools.wbs.web.common.model.RedmineIssue;
import com.ai_offshore.tools.wbs.web.master.category.mapper.model.Category;
import com.ai_offshore.tools.wbs.web.master.category.service.CategoryService;
import com.ai_offshore.tools.wbs.web.project.mapper.model.Project;
import com.ai_offshore.tools.wbs.web.project.service.ProjectService;

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
        // ステータス一覧を取得
        List<Category> statusCategories = categoryService.findByCategoryTypeCode("STATUS_REDMINE_KBN");
        model.addAttribute("statusList", statusCategories);
        
        // サービス区分の選択肢を取得
        List<Category> serviceCategories = categoryService.findByCategoryTypeCode("SERVICE_KBN");
        
        // 優先度一覧を取得
        List<Category> priorityCategories = categoryService.findByCategoryTypeCode("PRIORITY_KBN");
        model.addAttribute("priorityList", priorityCategories);
        
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
        projectService.create(project);
        redirectAttributes.addFlashAttribute("successMessage", "案件を登録しました");
        return "redirect:/project/regist";
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

    /**
     * 案件一覧画面を表示
     */
    @GetMapping("/list")
    public String showList(@RequestParam(required = false) String projectName, Model model) {
        List<Project> projects = projectService.findByProjectName(projectName);
        model.addAttribute("projects", projects);
        model.addAttribute("projectName", projectName);
        return "project/list";
    }

    /**
     * 案件を編集
     */
    @PostMapping("/edit")
    public String edit(@ModelAttribute Project project, RedirectAttributes redirectAttributes) {
        try {
            projectService.update(project);  // 更新処理を実行
            redirectAttributes.addFlashAttribute("successMessage", "案件を更新しました");
            return "redirect:/project/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "案件の更新に失敗しました: " + e.getMessage());
            return "redirect:/project/regist?ticketNumber=" + project.getTicketNumber();
        }
    }

    /**
     * 案件を削除
     */
    @PostMapping("delete/{ticketNumber}")
    @ResponseBody
    public void delete(@PathVariable String ticketNumber) {
        projectService.delete(ticketNumber);
    }
} 