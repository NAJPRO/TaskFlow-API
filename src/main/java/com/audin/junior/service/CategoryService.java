package com.audin.junior.service;

import java.util.List;

import com.audin.junior.dto.request.CategoryDTORequest;
import com.audin.junior.entity.Category;

public interface CategoryService {
    public Category store(CategoryDTORequest request);
    public Category findByIdAndUser(Integer id);


    List<Category> findAllByUser();
}
