package com.awbd.cookbase.controllers;

import com.awbd.cookbase.dtos.CategoryDTO;
import com.awbd.cookbase.dtos.RecipeDTO;
import com.awbd.cookbase.services.CategoryService;
import com.awbd.cookbase.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceControllerTest {

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    RecipeController recipeController;

    @Test
    public void edit_shouldReturnRecipeFormViewAndPopulateModel() {
        Long recipeId = 3L;
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(recipeId);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(4L);
        categoryDTO.setName("Dessert");

        when(recipeService.findById(recipeId)).thenReturn(recipeDTO);
        when(categoryService.findAll()).thenReturn(List.of(categoryDTO));

        String viewName = recipeController.edit(recipeId, model);

        assertEquals("recipeForm", viewName);

        ArgumentCaptor<RecipeDTO> recipeCaptor = ArgumentCaptor.forClass(RecipeDTO.class);
        verify(model).addAttribute(eq("recipe"), recipeCaptor.capture());
        assertEquals(recipeId, recipeCaptor.getValue().getId());

        verify(model).addAttribute(eq("categoriesAll"), eq(List.of(categoryDTO)));
        verify(recipeService, times(1)).findById(recipeId);
        verify(categoryService, times(1)).findAll();
    }

    @Test
    public void viewRecipes_shouldAddRecipesToModelAndReturnView() {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(1L);
        dto.setTitle("Test Recipe");

        when(recipeService.findAll()).thenReturn(List.of(dto));

        String viewName = recipeController.viewRecipes(model);

        assertEquals("viewRecipes", viewName);
        verify(recipeService, times(1)).findAll();
        verify(model).addAttribute(eq("recipes"), eq(List.of(dto)));
    }

    @Test
    public void showRecipe_shouldReturnDetailsViewAndAddRecipeToModel() {
        Long recipeId = 3L;
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(recipeId);
        recipeDTO.setTitle("Chocolate Cake");

        when(recipeService.findDetailsById(recipeId)).thenReturn(recipeDTO);

        String viewName = recipeController.showRecipe(recipeId, model);

        assertEquals("recipeDetails", viewName);
        verify(recipeService).findDetailsById(recipeId);
        verify(model).addAttribute("recipe", recipeDTO);
    }


}
