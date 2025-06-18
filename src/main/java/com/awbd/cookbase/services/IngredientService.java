package com.awbd.cookbase.services;

import com.awbd.cookbase.domain.Ingredient;
import com.awbd.cookbase.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Optional<Ingredient> findById(Long id) {
        return ingredientRepository.findById(id);
    }

    public List<Ingredient> findByRecipeId(Long recipeId) {
        return ingredientRepository.findByRecipeId(recipeId);
    }

    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteById(Long id) {
        ingredientRepository.deleteById(id);
    }
}
