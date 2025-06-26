package com.awbd.cookbase.repositories;


import com.awbd.cookbase.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("h2")
public class CascadeTypesTest {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    public void insertRecipe() {
        User user = new User();
        user.setUsername("newuser");
        user.setEmail("newuser@example.com");
        user.setPassword("pass123");

        Recipe recipe = new Recipe();
        recipe.setTitle("Test Recipe");
        recipe.setDescription("This is a test");
        recipe.setPrepTimeInMinutes(10);
        recipe.setCookTimeInMinutes(20);
        recipe.setServings(2);
        recipe.setDifficulty(Difficulty.Easy);
        recipe.setAuthor(user);

        Ingredient ingredient = new Ingredient();
        ingredient.setName("Tomato");
        ingredient.setQuantity("2");
        ingredient.setRecipe(recipe);

        Step step = new Step();
        step.setStepNumber(1);
        step.setInstruction("Chop the tomatoes");
        step.setRecipe(recipe);

        recipe.setIngredients(List.of(ingredient));
        recipe.setSteps(List.of(step));

        userRepository.save(user);
        recipeRepository.save(recipe);

        Optional<Recipe> recipeOpt = recipeRepository.findByTitle("Test Recipe");
        assertTrue(recipeOpt.isPresent()); //verifica daca gaseste reteta

        Recipe savedRecipe = recipeOpt.get();
        assertEquals(1, savedRecipe.getIngredients().size());
        assertEquals(1, savedRecipe.getSteps().size());
    }


    @Test
    public void deleteRecipeShouldRemoveIngredientsAndSteps() {
        Optional<Recipe> recipeOpt = recipeRepository.findById(1L);
        assertTrue(recipeOpt.isPresent());

        recipeRepository.deleteById(1L);

        assertFalse(recipeRepository.findById(1L).isPresent());
    }


    @Test
    public void updateTitleAndIngredients() {
        Optional<Recipe> recipeOpt = recipeRepository.findById(1L);
        assertTrue(recipeOpt.isPresent());

        Recipe recipe = recipeOpt.get();
        recipe.setTitle("Updated Title");

        // Update ingredient
        List<Ingredient> ingredients = recipe.getIngredients();
        assertFalse(ingredients.isEmpty());
        ingredients.get(0).setQuantity("3 pieces");

        recipeRepository.save(recipe);

        Recipe updated = recipeRepository.findById(1L).orElseThrow();
        assertEquals("Updated Title", updated.getTitle());
        assertEquals("3 pieces", updated.getIngredients().get(0).getQuantity());
    }

    @Test
    public void updateStepInstruction() {
        Optional<Recipe> recipeOpt = recipeRepository.findById(1L);
        assertTrue(recipeOpt.isPresent());

        Recipe recipe = recipeOpt.get();
        List<Step> steps = recipe.getSteps();
        assertFalse(steps.isEmpty());

        steps.get(0).setInstruction("Updated instruction text");
        recipeRepository.save(recipe);

        Recipe updated = recipeRepository.findById(1L).orElseThrow();
        assertEquals("Updated instruction text", updated.getSteps().get(0).getInstruction());
    }




}
