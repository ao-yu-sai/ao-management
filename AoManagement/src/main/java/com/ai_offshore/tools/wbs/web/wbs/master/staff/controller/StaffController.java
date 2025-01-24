package com.ai_offshore.tools.wbs.web.wbs.master.staff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_offshore.tools.wbs.web.master.staff.model.Staff;
import com.ai_offshore.tools.wbs.web.master.staff.service.StaffService;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/list")
    public List<Staff> getStaffList() {
        return staffService.findAll();
    }

    @GetMapping("/{id}")
    public Staff getStaff(@PathVariable Long id) {
        return staffService.findById(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> updateStaff(@PathVariable Long id,
            @RequestBody Staff staff) {
        staff.setStaffId(id);
        staffService.update(staff);
        return ResponseEntity.ok("更新しました");
    }

    @PostMapping
    public ResponseEntity<String> createStaff(@RequestBody Staff staff) {
        staffService.create(staff);
        return ResponseEntity.ok("登録しました");
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id) {
        staffService.delete(id);
        return ResponseEntity.ok("削除しました");
    }
}