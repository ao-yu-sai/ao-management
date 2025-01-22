package com.ai_offshore.tools.web.wbs.category.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ai_offshore.tools.web.wbs.category.mapper.model.Category;

@Mapper
public interface CategoryMapper {
    @Select("SELECT category_name, category_code, " +
            "category_type_code, description, display_order, is_active " +
            "FROM category " +
            "WHERE category_type_code = #{categoryTypeCode} " +
            "ORDER BY display_order")
    List<Category> findByCategoryTypeCode(String categoryTypeCode);

    @Select("SELECT category_name, category_code, " +
            "category_type_code, description, display_order, is_active " +
            "FROM category " +
            "WHERE category_type_code = #{categoryTypeCode} " +
            "AND category_code = #{categoryCode}")
    Category findByTypeCodeAndCode(String categoryTypeCode, String categoryCode);

    @Insert("INSERT INTO category (category_name, category_code, " +
            "category_type_code, description, display_order, is_active) " +
            "VALUES (#{categoryName}, #{categoryCode}, " +
            "#{categoryTypeCode}, #{description}, #{displayOrder}, #{isActive})")
    void insert(Category category);

    @Update("UPDATE category SET " +
            "category_code = #{newCategoryCode}, " +
            "category_name = #{categoryName}, " +
            "description = #{description}, " +
            "display_order = #{displayOrder}, " +
            "is_active = #{isActive} " +
            "WHERE category_type_code = #{categoryTypeCode} " +
            "AND category_code = #{categoryCode}")
    void update(Category category);

    @Delete("DELETE FROM category " +
            "WHERE category_type_code = #{categoryTypeCode} " +
            "AND category_code = #{categoryCode}")
    void delete(String categoryTypeCode, String categoryCode);
} 