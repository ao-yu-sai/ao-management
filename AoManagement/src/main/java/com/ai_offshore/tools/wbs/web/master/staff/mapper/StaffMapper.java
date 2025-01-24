package com.ai_offshore.tools.wbs.web.master.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ai_offshore.tools.wbs.web.master.staff.model.Staff;

@Mapper
public interface StaffMapper {

    @Select("SELECT * FROM staff ORDER BY staff_id")
    List<Staff> findAll();

    @Select("SELECT * FROM staff WHERE staff_id = #{staffId}")
    Staff findById(Long staffId);

    @Insert("INSERT INTO staff (staff_name, email, department, is_active) "
            + "VALUES (#{staffName}, #{email}, #{department}, #{isActive})")
    void insert(Staff staff);

    @Update("UPDATE staff SET staff_name = #{staffName}, email = #{email}, "
            + "department = #{department}, is_active = #{isActive} "
            + "WHERE staff_id = #{staffId}")
    void update(Staff staff);

    @Delete("DELETE FROM staff WHERE staff_id = #{staffId}")
    void delete(Long staffId);
}