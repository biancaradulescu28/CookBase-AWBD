package com.awbd.cookbase.services;

import com.awbd.cookbase.dtos.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();
    CategoryDTO findById(Long id);
    CategoryDTO save(CategoryDTO dto);
    void deleteById(Long id);
}
