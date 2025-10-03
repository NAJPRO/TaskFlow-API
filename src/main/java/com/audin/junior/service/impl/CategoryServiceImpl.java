package com.audin.junior.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.audin.junior.dto.request.CategoryDTORequest;
import com.audin.junior.entity.Category;
import com.audin.junior.entity.User;
import com.audin.junior.repository.CategoryRepository;
import com.audin.junior.service.CategoryService;
import com.audin.junior.utils.AuthUtils;

import lombok.AllArgsConstructor;

import com.audin.junior.mapper.CategoryMapper;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository repository;
    private final CategoryMapper categoryMapper;
    private final AuthUtils authUtils;
   
    @Override
    public Category store(CategoryDTORequest request){
        User user = authUtils.getCurrentUser(); 

        Category category = this.categoryMapper.toEntity(request);
        return this.repository.findByNameAndUser(category.getName(), user).orElseGet(
            () -> {
                category.setUser(user);
                return this.repository.save(category);
            }
        );
    }

    @Override
    public Category findByIdAndUser(Integer id){
        User user = authUtils.getCurrentUser(); 

        Category category = this.repository.findByIdAndUser(id, user).orElseThrow();
        return category;
    }

    @Override
    public List<Category> findAllByUser() {
        User user = authUtils.getCurrentUser();
        List<Category> categories = this.repository.findAllByUser(user);
        return categories;
    }

    
}
