package com.ai_offshore.tools.web.wbs.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String dashboard(Model model, HttpServletRequest request) {
        model.addAttribute("currentPage", "dashboard");
        return "dashboard/index";
    }
}