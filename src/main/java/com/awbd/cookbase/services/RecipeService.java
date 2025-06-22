package com.awbd.cookbase.services;

import com.awbd.cookbase.domain.Recipe;
import com.awbd.cookbase.dtos.RecipeDTO;


import java.util.List;
import java.util.Optional;


public interface RecipeService {

    List<RecipeDTO> findAll();
    void deleteById(Long id);
    RecipeDTO findById(Long l);
    RecipeDTO save(RecipeDTO recipe);
    RecipeDTO findDetailsById(Long id);
    List<RecipeDTO> findAllByCategory(Long categoryId);
    void addIngredient(Long recipeId, Long ingredientId, String newName, String quantity);
    void addStep(Long recipeId, int stepNumber, String instruction);
    void addReview(Long recipeId, int rating, String comment);


}
