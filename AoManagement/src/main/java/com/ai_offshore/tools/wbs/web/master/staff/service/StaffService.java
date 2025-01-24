package com.ai_offshore.tools.wbs.web.master.staff.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_offshore.tools.wbs.web.master.staff.mapper.StaffMapper;
import com.ai_offshore.tools.wbs.web.master.staff.model.Staff;

@Service
public class StaffService {

    private final StaffMapper staffMapper;

    public StaffService(StaffMapper staffMapper) {
        this.staffMapper = staffMapper;
    }

    public List<Staff> findAll() {
        return staffMapper.findAll();
    }

    public Staff findById(Long id) {
        return staffMapper.findById(id);
    }

    @Transactional
    public void create(Staff staff) {
        staffMapper.insert(staff);
    }

    @Transactional
    public void update(Staff staff) {
        staffMapper.update(staff);
    }

    @Transactional
    public void delete(Long id) {
        staffMapper.delete(id);
    }
}