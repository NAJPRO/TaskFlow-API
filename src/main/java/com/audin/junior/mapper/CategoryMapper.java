package com.audin.junior.mapper;

import java.util.List;

import com.audin.junior.dto.request.CategoryDTORequest;
import com.audin.junior.dto.response.CategoryDTOResponse;
import com.audin.junior.entity.Category;

public interface CategoryMapper {
    public Category toEntity(CategoryDTORequest request);
    public CategoryDTOResponse toDto(Category category);
    public List<CategoryDTOResponse> toDto(List<Category> category);

}
