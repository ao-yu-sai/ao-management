package com.ai_offshore.tools.web.wbs.master.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ai_offshore.tools.web.wbs.master.category.mapper.CategoryTypeMapper;
import com.ai_offshore.tools.web.wbs.master.category.mapper.model.Category;
import com.ai_offshore.tools.web.wbs.master.category.mapper.model.CategoryType;

@Service
public class CategoryTypeService {

    private final CategoryTypeMapper categoryTypeMapper;
    private final CategoryService categoryService;

    public CategoryTypeService(CategoryTypeMapper categoryTypeMapper,
            CategoryService categoryService) {
        this.categoryTypeMapper = categoryTypeMapper;
        this.categoryService = categoryService;
    }

    public List<CategoryType> findAll() {
        return categoryTypeMapper.findAll();
    }

    public CategoryType findByCode(String categoryTypeCode) {
        return categoryTypeMapper.findByCode(categoryTypeCode);
    }

    public void create(CategoryType categoryType) {
        categoryType.setIsActive(true);
        categoryTypeMapper.insert(categoryType);
    }

    public void update(CategoryType categoryType) {
        categoryTypeMapper.update(categoryType);
    }

    public void delete(String categoryTypeCode) {
        List<Category> categories = categoryService
                .findByCategoryTypeCode(categoryTypeCode);
        for (Category category : categories) {
            categoryService.delete(categoryTypeCode,
                    category.getCategoryCode());
        }

        categoryTypeMapper.delete(categoryTypeCode);
    }
}