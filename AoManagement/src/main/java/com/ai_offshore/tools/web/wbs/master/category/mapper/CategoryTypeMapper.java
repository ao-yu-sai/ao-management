package com.ai_offshore.tools.web.wbs.master.category.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ai_offshore.tools.web.wbs.master.category.mapper.model.CategoryType;

@Mapper
public interface CategoryTypeMapper {
    List<CategoryType> findAll();

    CategoryType findByCode(String categoryTypeCode);

    void insert(CategoryType categoryType);

    void update(CategoryType categoryType);

    void delete(String categoryTypeCode);
}