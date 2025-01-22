package com.ai_offshore.tools.web.wbs.category.mapper.model;

import lombok.Data;

@Data
public class Category {
    private String categoryName;
    private String categoryCode;
    private String newCategoryCode;
    private String categoryTypeCode;
    private String description;
    private Integer displayOrder;
    private Boolean isActive;
    
    // 関連エンティティ
    private CategoryType categoryType;
    
    public String getCategoryCode() {
        return categoryCode;
    }
    
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
    
    public String getCategoryTypeCode() {
        return categoryTypeCode;
    }
    
    public void setCategoryTypeCode(String categoryTypeCode) {
        this.categoryTypeCode = categoryTypeCode;
    }
    
    public String getNewCategoryCode() {
        return newCategoryCode;
    }
    
    public void setNewCategoryCode(String newCategoryCode) {
        this.newCategoryCode = newCategoryCode;
    }
} 