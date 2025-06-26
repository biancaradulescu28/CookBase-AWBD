package com.awbd.cookbase.repositories;

import com.awbd.cookbase.domain.Category;
import com.awbd.cookbase.domain.Difficulty;
import com.awbd.cookbase.domain.Recipe;
import com.awbd.cookbase.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("h2")
@Slf4j
public class RecipeRepositoryTest {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeRepositoryTest(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Test
    public void findById_shouldReturnRecipe() {
        Optional<Recipe> recipeOpt = recipeRepository.findById(3L);
        assertTrue(recipeOpt.isPresent());

        Recipe recipe = recipeOpt.get();
        assertEquals("Chocolate Cake", recipe.getTitle());
        log.info("Recipe found by ID 3: {}", recipe.getTitle());
    }

    @Test
    public void findByTitleLike_shouldReturnRecipe() {
        List<Recipe> recipes = recipeRepository.findByTitleLike("%Cake%");
        assertFalse(recipes.isEmpty());

        recipes.forEach(r -> log.info("Recipe found with title like '%Cake%': {}", r.getTitle()));
    }

    @Test
    public void findAllWithCategories_shouldReturnRecipeWithCategory() {
        Iterable<Recipe> recipes = recipeRepository.findAllWithCategories(Sort.by("title"));
        assertTrue(recipes.iterator().hasNext());

        recipes.forEach(recipe -> {
            log.info("Recipe: {}", recipe.getTitle());
            recipe.getCategories().forEach(category -> log.info(" - Category: {}", category.getName()));
        });
    }

    @Test
    public void findByIdWithDetails_shouldReturnRecipeWithCategory() {
        Optional<Recipe> recipeOpt = recipeRepository.findByIdWithDetails(3L);
        assertTrue(recipeOpt.isPresent());

        Recipe recipe = recipeOpt.get();
        assertEquals("Chocolate Cake", recipe.getTitle());
        assertFalse(recipe.getCategories().isEmpty());
        log.info("Recipe with ID 3 has categories:");
        recipe.getCategories().forEach(c -> log.info(" - {}", c.getName()));
    }

}
