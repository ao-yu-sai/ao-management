package com.ai_offshore.tools.web.wbs.master.task.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ai_offshore.tools.web.wbs.master.task.mapper.model.Task;

@Mapper
public interface TaskMapper {
    
    @Select("SELECT t.*, " +
            "c.category_name as \"taskKbn.categoryName\", " +
            "c.category_code as \"taskKbn.categoryCode\" " +
            "FROM project_function_task_info t " +
            "LEFT JOIN category c ON t.task_kbn_code = c.category_code " +
            "AND c.category_type_code = 'TASK_KBN' " +
            "WHERE t.service_kbn_code = #{serviceKbnCode} " +
            "AND t.ticket_number = #{ticketNumber} " +
            "AND t.function_code = #{functionCode} " +
            "AND t.task_kbn_code = #{taskKbnCode} " +
            "AND t.person_in_charge = #{personInCharge}")
    Task findByCompositeKey(@Param("serviceKbnCode") String serviceKbnCode,
                           @Param("ticketNumber") String ticketNumber,
                           @Param("functionCode") String functionCode,
                           @Param("taskKbnCode") String taskKbnCode,
                           @Param("personInCharge") String personInCharge);
    
    @Insert("INSERT INTO project_function_task_info (" +
            "ticket_number, service_kbn_code, function_code, task_kbn_code, " +
            "task_name, person_in_charge, planned_start_date, planned_end_date, " +
            "planned_man_hours, actual_start_date, actual_end_date, " +
            "actual_man_hours, progress_rate) " +
            "VALUES (" +
            "#{ticketNumber}, #{serviceKbnCode}, #{functionCode}, #{taskKbnCode}, " +
            "#{taskName}, #{personInCharge}, #{plannedStartDate}, #{plannedEndDate}, " +
            "#{plannedManHours}, #{actualStartDate}, #{actualEndDate}, " +
            "#{actualManHours}, #{progressRate})")
    void insert(Task task);
    
    @Delete("DELETE FROM project_function_task_info " +
            "WHERE service_kbn_code = #{serviceKbnCode} " +
            "AND ticket_number = #{ticketNumber} " +
            "AND function_code = #{functionCode} " +
            "AND task_kbn_code = #{taskKbnCode} " +
            "AND person_in_charge = #{personInCharge}")
    void deleteByCompositeKey(@Param("serviceKbnCode") String serviceKbnCode,
                             @Param("ticketNumber") String ticketNumber,
                             @Param("functionCode") String functionCode,
                             @Param("taskKbnCode") String taskKbnCode,
                             @Param("personInCharge") String personInCharge);
}