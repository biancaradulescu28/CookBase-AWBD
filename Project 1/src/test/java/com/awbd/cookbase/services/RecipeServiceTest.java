package com.awbd.cookbase.services;

import com.awbd.cookbase.domain.Category;
import com.awbd.cookbase.domain.Difficulty;
import com.awbd.cookbase.domain.Recipe;
import com.awbd.cookbase.dtos.RecipeDTO;
import com.awbd.cookbase.mappers.IngredientMapper;
import com.awbd.cookbase.mappers.ReviewMapper;
import com.awbd.cookbase.mappers.StepMapper;
import com.awbd.cookbase.repositories.CategoryRepository;
import com.awbd.cookbase.repositories.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private IngredientMapper ingredientMapper;

    @Mock
    private StepMapper stepMapper;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    public void findAll_shouldReturnMappedDtos() {
        Recipe recipe = new Recipe();
        recipe.setId(3L);
        recipe.setTitle("Chocolate Cake");
        recipe.setCategories(List.of(new Category()));

        RecipeDTO dto = new RecipeDTO();
        dto.setId(3L);
        dto.setTitle("Chocolate Cake");

        when(recipeRepository.findAllWithCategories(Sort.by("title"))).thenReturn(List.of(recipe));
        when(modelMapper.map(recipe, RecipeDTO.class)).thenReturn(dto);

        List<RecipeDTO> result = recipeService.findAll();

        assertEquals(1, result.size());
        assertEquals("Chocolate Cake", result.get(0).getTitle());
        verify(recipeRepository, times(1)).findAllWithCategories(any());
        verify(modelMapper, times(1)).map(recipe, RecipeDTO.class);
    }

    @Test
    public void findById_shouldThrowExceptionIfNotFound() {
        when(recipeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> recipeService.findById(99L));
    }

    @Test
    public void deleteById_shouldInvokeRepo() {
        recipeService.deleteById(3L);
        verify(recipeRepository, times(1)).deleteById(3L);
    }

}
