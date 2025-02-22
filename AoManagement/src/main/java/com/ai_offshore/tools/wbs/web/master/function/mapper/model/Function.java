package com.ai_offshore.tools.wbs.web.master.function.mapper.model;

import java.time.LocalDateTime;

import com.ai_offshore.tools.wbs.web.master.category.mapper.model.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Function {
    private String serviceKbnCode;
    private String functionCode;
    private String functionName;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Category category;
}