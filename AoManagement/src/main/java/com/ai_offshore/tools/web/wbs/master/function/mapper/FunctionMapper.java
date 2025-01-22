package com.ai_offshore.tools.web.wbs.master.function.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ai_offshore.tools.web.wbs.master.function.mapper.model.Function;

@Mapper
public interface FunctionMapper {
    
    @Select("SELECT f.*, c.category_name as \"category.categoryName\" " +
            "FROM function_info f " +
            "LEFT JOIN category c ON f.service_kbn_code = c.category_code " +
            "AND c.category_type_code = 'SERVICE_KBN' " +
            "ORDER BY f.service_kbn_code, f.function_code")
    List<Function> findAll();

    @Select("SELECT f.*, c.category_name as \"category.categoryName\" "
            + "FROM function_info f "
            + "LEFT JOIN category c ON f.service_kbn_code = c.category_code "
            + "AND c.category_type_code = 'SERVICE_KBN' "
            + "WHERE f.function_code like CONCAT('%', #{keyword}, '%') "
            + "OR f.function_name like CONCAT('%', #{keyword}, '%') "
            + "OR f.description like CONCAT('%', #{keyword}, '%') "
            + "ORDER BY f.service_kbn_code, f.function_code")
    List<Function> findDataByKeyword(String keyword);

    @Select("SELECT * FROM function_info " +
            "WHERE service_kbn_code = #{serviceKbnCode} " +
            "ORDER BY function_code")
    List<Function> findByServiceKbnCode(String serviceKbnCode);

    @Insert("INSERT INTO function_info (service_kbn_code, function_code, function_name, " +
            "description, is_active) " +
            "VALUES (#{serviceKbnCode}, #{functionCode}, #{functionName}, " +
            "#{description}, #{isActive})")
    void insert(Function function);

    @Select("SELECT f.*, c.category_name as \"category.categoryName\" " +
            "FROM function_info f " +
            "LEFT JOIN category c ON f.service_kbn_code = c.category_code " +
            "AND c.category_type_code = 'SERVICE_KBN' " +
            "WHERE f.function_code = #{functionCode}")
    Function findById(String functionCode);

    @Update("UPDATE function_info SET " +
            "service_kbn_code = #{serviceKbnCode}, " +
            "function_name = #{functionName}, " +
            "description = #{description}, " +
            "is_active = #{isActive} " +
            "WHERE function_code = #{functionCode}")
    void update(Function function);

    @Delete("DELETE FROM function_info WHERE function_code = #{functionCode}")
    void delete(String functionCode);
} 