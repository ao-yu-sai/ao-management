package com.ai_offshore.tools.wbs.web.wbs.master.staff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai_offshore.tools.wbs.web.master.staff.service.StaffService;

@Controller
@RequestMapping("/master/staff")
public class StaffMasterController {

    private final StaffService staffService;

    public StaffMasterController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("staffList", staffService.findAll());
        return "master/staff/list";
    }
}