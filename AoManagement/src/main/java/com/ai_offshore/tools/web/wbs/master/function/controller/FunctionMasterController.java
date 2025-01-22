package com.ai_offshore.tools.web.wbs.master.function.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai_offshore.tools.web.wbs.category.service.CategoryService;
import com.ai_offshore.tools.web.wbs.master.function.mapper.FunctionMapper;
import com.ai_offshore.tools.web.wbs.master.function.mapper.model.Function;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/master/functions")
public class FunctionMasterController {
    
    private final FunctionMapper functionMapper;
    private final CategoryService categoryService;
    
    public FunctionMasterController(FunctionMapper functionMapper, CategoryService categoryService) {
        this.functionMapper = functionMapper;
        this.categoryService = categoryService;
    }
    
    @GetMapping
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) String keyword,
                       Model model) {
        PageHelper.startPage(page, size);
        List<Function> functions = new ArrayList<>();
        if (ObjectUtils.isEmpty(keyword)) {
            functions = functionMapper.findAll();
        } else {
            functions = functionMapper.findDataByKeyword(keyword);
        }
        
//        if (keyword != null && !keyword.isEmpty()) {
//            functions = functions.stream()
//                .filter(f -> 
//                    (f.getFunctionCode() != null && f.getFunctionCode().contains(keyword)) ||
//                    (f.getFunctionName() != null && f.getFunctionName().contains(keyword)) ||
//                    (f.getDescription() != null && f.getDescription().contains(keyword)) ||
//                    (f.getCategory() != null && f.getCategory().getCategoryName() != null && 
//                     f.getCategory().getCategoryName().contains(keyword))
//                )
//                .collect(Collectors.toList());
//        }
        
        PageInfo<Function> pageInfo = new PageInfo<>(functions);
        
        model.addAttribute("functions", pageInfo.getList());
        model.addAttribute("categories", categoryService.findByCategoryTypeCode("SERVICE_KBN"));
        model.addAttribute("currentPage", pageInfo.getPageNum());
        model.addAttribute("totalPages", pageInfo.getPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("total", pageInfo.getTotal());
        model.addAttribute("keyword", keyword);
        return "master/function/list";
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Function> getFunction(@PathVariable String id) {
        try {
            Function function = functionMapper.findById(id);
            if (function != null) {
                return ResponseEntity.ok(function);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
    @ResponseBody
    public ResponseEntity<String> create(@ModelAttribute Function function) {
        try {
            functionMapper.insert(function);
            return ResponseEntity.ok("機能を登録しました");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("登録に失敗しました: " + e.getMessage());
        }
    }
    
    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable String id, @ModelAttribute Function function) {
        try {
            function.setFunctionCode(id);
            functionMapper.update(function);
            return ResponseEntity.ok("機能を更新しました");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("更新に失敗しました: " + e.getMessage());
        }
    }
    
    @PostMapping("/{id}/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            functionMapper.delete(id);
            return ResponseEntity.ok("機能を削除しました");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("削除に失敗しました: " + e.getMessage());
        }
    }
} 