package com.audin.junior.controller;

import org.springframework.web.bind.annotation.RestController;

import com.audin.junior.dto.request.CategoryDTORequest;
import com.audin.junior.dto.response.CategoryDTOResponse;
import com.audin.junior.entity.Category;
import com.audin.junior.mapper.CategoryMapper;
import com.audin.junior.service.CategoryService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@RestController
@AllArgsConstructor
@RequestMapping(path = "categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    @PostMapping
    public ResponseEntity<CategoryDTOResponse> store(@RequestBody CategoryDTORequest request) {
        Category category = this.categoryService.store(request);
        return ResponseEntity.ok(categoryMapper.toDto(category));
    }

    @GetMapping
    public @ResponseBody List<CategoryDTOResponse> getMethodName() {
        List<Category> categories = categoryService.findAllByUser();
        return categoryMapper.toDto(categories);
    }
    
    
}
