package com.ai_offshore.tools.web.wbs.category.mapper.model;

import lombok.Data;

@Data
public class CategoryType {
    private String categoryTypeCode;
    private String categoryTypeName;
    private String description;
    private Boolean isActive;
}