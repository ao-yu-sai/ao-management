package com.ai_offshore.tools.wbs.web.master.category.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai_offshore.tools.wbs.web.wbs.master.category.mapper.model.Category;
import com.ai_offshore.tools.wbs.web.wbs.master.category.mapper.model.CategoryType;
import com.ai_offshore.tools.wbs.web.wbs.master.category.service.CategoryService;
import com.ai_offshore.tools.wbs.web.wbs.master.category.service.CategoryTypeService;

/**
 * 区分種別マスターのコントローラークラス
 */
@Controller
@RequestMapping("/master/category")
public class CategoryTypeController {

    private static final Logger log = LoggerFactory
            .getLogger(CategoryTypeController.class);

    private final CategoryTypeService categoryTypeService;
    private final CategoryService categoryService;

    public CategoryTypeController(CategoryTypeService categoryTypeService,
            CategoryService categoryService) {
        this.categoryTypeService = categoryTypeService;
        this.categoryService = categoryService;
    }

    /**
     * ★区分種別マスター一覧画面を表示
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("categoryTypes", categoryTypeService.findAll());
        return "master/category/list";
    }

    /**
     * ★区分一覧を取得（フラグメント） GET /master/category/{code}/categories
     */
    @GetMapping("/{code}/categories")
    public String getCategoryList(@PathVariable("code") String categoryTypeCode,
            Model model) {
        log.debug("Getting categories for type code: {}", categoryTypeCode);
        List<Category> categories = categoryService
                .findByCategoryTypeCode(categoryTypeCode);
        log.debug("Found {} categories", categories.size());
        model.addAttribute("categories", categories);
        return "master/category/category-list-fragment";
    }

    /**
     * ★区分種別マスターを新規登録
     */
    @PostMapping
    public String create(@ModelAttribute CategoryType categoryType) {
        categoryTypeService.create(categoryType);
        return "redirect:/master/category";
    }

    /**
     * ★区分種別マスター編集画面を表示 GET /master/category/{code}/edit
     */
    @GetMapping("/{code}/edit")
    public String editForm(@PathVariable String code, Model model) {
        model.addAttribute("categoryType",
                categoryTypeService.findByCode(code));
        return "category/type/form";
    }

    /**
     * ★区分種別マスターを更新
     */
    @PostMapping("/{code}")
    public String update(@PathVariable String code,
            @ModelAttribute CategoryType categoryType) {
        categoryType.setCategoryTypeCode(code);
        categoryTypeService.update(categoryType);
        return "redirect:/master/category";
    }

    /**
     * ★区分種別マスターを削除 POST /master/category/{code}/delete
     */
    @PostMapping("/{code}/delete")
    public String delete(@PathVariable String code) {
        categoryTypeService.delete(code);
        return "redirect:/master/category";
    }

    /**
     * ★区分種別マスターの詳細情報を取得（API） GET /master/category/{code}
     */
    @GetMapping("/{code}")
    @ResponseBody
    public CategoryType getDetail(@PathVariable String code) {
        return categoryTypeService.findByCode(code);
    }

    /**
     * ★区分を保存（API） POST /master/category/{code}/categories
     */
    @PostMapping("/{code}/categories")
    @ResponseBody
    public String saveCategory(@PathVariable("code") String categoryTypeCode,
            @ModelAttribute Category category) {
        category.setCategoryTypeCode(categoryTypeCode);
        Category existingCategory = categoryService.findByTypeCodeAndCode(
                categoryTypeCode, category.getCategoryCode());
        if (existingCategory == null) {
            // 新規登録の場合
            category.setIsActive(true);
            categoryService.create(category);
        } else {
            // 更新の場合
            category.setIsActive(existingCategory.getIsActive());
            categoryService.update(category);
        }
        return "success";
    }

    /**
     * ★区分を削除（API） POST /master/category/{typeCode}/categories/{code}/delete
     */
    @PostMapping("/{typeCode}/categories/{code}/delete")
    @ResponseBody
    public String deleteCategory(@PathVariable String typeCode,
            @PathVariable String code) {
        categoryService.delete(typeCode, code);
        return "success";
    }

    /**
     * ★区分を更新（API）
     * POST /master/category/{typeCode}/categories/{code}
     */
    @PostMapping("/{typeCode}/categories/{code}")
    @ResponseBody
    public String updateCategory(
            @PathVariable("typeCode") String categoryTypeCode,
            @PathVariable("code") String code,
            @ModelAttribute Category category) {
        category.setCategoryTypeCode(categoryTypeCode);
        categoryService.update(category);
        return "success";
    }
}