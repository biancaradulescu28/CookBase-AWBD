package com.awbd.cookbase.mappers;

import com.awbd.cookbase.domain.Category;
import com.awbd.cookbase.dtos.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDTO toDto(Category category) {
        Long id = category.getId();
        String name= category.getName();
        return new CategoryDTO(id, name);
    }

    public Category toCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }
}

