package com.awbd.cookbase.services;


import com.awbd.cookbase.domain.Category;
import com.awbd.cookbase.dtos.CategoryDTO;
import com.awbd.cookbase.mappers.CategoryMapper;
import com.awbd.cookbase.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void findAll_shouldReturnMappedDTOs() {
        // Simulăm o listă cu o categorie existentă (dar nu o „creăm” în DB)
        Category category = new Category();
        category.setId(4L);
        category.setName("Dessert");

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(4L);
        categoryDTO.setName("Dessert");

        // Mocking: repo returnează lista cu 1 Category
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDTO);

        // Apelăm metoda reală
        List<CategoryDTO> result = categoryService.findAll();

        // Verificări
        assertEquals(1, result.size());
        assertEquals("Dessert", result.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
        verify(categoryMapper, times(1)).toDto(category);
    }


    @Test
    public void findById_shouldReturnCategoryDTO() {
        Category category = new Category();
        category.setId(4L);
        category.setName("Dessert");

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(4L);
        categoryDTO.setName("Dessert");

        when(categoryRepository.findById(4L)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.findById(4L);

        assertNotNull(result);
        assertEquals("Dessert", result.getName());
        verify(categoryRepository, times(1)).findById(4L);
        verify(categoryMapper, times(1)).toDto(category);
    }

    @Test
    public void save_shouldReturnSavedCategoryDTO() {
        CategoryDTO inputDto = new CategoryDTO();
        inputDto.setName("New Category");

        Category category = new Category();
        category.setName("New Category");

        Category savedCategory = new Category();
        savedCategory.setId(10L);
        savedCategory.setName("New Category");

        CategoryDTO outputDto = new CategoryDTO();
        outputDto.setId(10L);
        outputDto.setName("New Category");

        when(categoryMapper.toCategory(inputDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryMapper.toDto(savedCategory)).thenReturn(outputDto);

        CategoryDTO result = categoryService.save(inputDto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("New Category", result.getName());

        verify(categoryMapper).toCategory(inputDto);
        verify(categoryRepository).save(category);
        verify(categoryMapper).toDto(savedCategory);
    }

    @Test
    public void deleteById_shouldInvokeRepository() {
        Long idToDelete = 5L;
        doNothing().when(categoryRepository).deleteById(idToDelete);

        categoryService.deleteById(idToDelete);

        verify(categoryRepository, times(1)).deleteById(idToDelete);
    }


}
