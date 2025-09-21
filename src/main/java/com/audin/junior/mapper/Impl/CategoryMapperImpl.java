package com.audin.junior.mapper.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.audin.junior.dto.request.CategoryDTORequest;
import com.audin.junior.dto.response.CategoryDTOResponse;
import com.audin.junior.entity.Category;
import com.audin.junior.mapper.CategoryMapper;

@Component
public class CategoryMapperImpl implements CategoryMapper{

    @Override
    public Category toEntity(CategoryDTORequest request) {
        Category category = new Category();
        category.setName(request.name());
        return category;
    }

    @Override
    public CategoryDTOResponse toDto(Category category) {
        CategoryDTOResponse response = new CategoryDTOResponse(
            category.getId(),
            category.getName(),
            category.getCreatedAt()
        );
        return response;
    }

    @Override
    public List<CategoryDTOResponse> toDto(List<Category> category) {
        List<CategoryDTOResponse> categoriesResponses = new ArrayList<CategoryDTOResponse>();
        for (Category cat : category) {
            categoriesResponses.add(this.toDto(cat));
        }
        return categoriesResponses;
    } 

}
