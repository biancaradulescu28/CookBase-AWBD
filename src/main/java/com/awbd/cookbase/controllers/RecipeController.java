package com.awbd.cookbase.controllers;

import com.awbd.cookbase.domain.Recipe;
import com.awbd.cookbase.dtos.CategoryDTO;
import com.awbd.cookbase.dtos.RecipeDTO;
import com.awbd.cookbase.services.CategoryService;
import com.awbd.cookbase.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@RequestMapping("/recipes")
public class RecipeController {

    RecipeService recipeService;
    CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @RequestMapping({"", "/"})
    public String viewRecipes(Model model) {
        List<RecipeDTO> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);
        return "viewRecipes";
    }


    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable String id){
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/recipes";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        RecipeDTO dto = recipeService.findById(id);
        model.addAttribute("recipe", dto);               // ①  use the same key as the form
        model.addAttribute("categoriesAll", categoryService.findAll());
        return "recipeForm";                             // ②  file name WITHOUT .html
    }

    @PostMapping        // handles POST /recipes
    public String saveOrUpdate(@ModelAttribute("recipe") RecipeDTO recipe) {

        recipeService.save(recipe);   // insert or update

        return "redirect:/recipes";   // go back to the table
    }

    @GetMapping("/{id}")
    public String showRecipe(@PathVariable Long id, Model model) {
        RecipeDTO recipe = recipeService.findDetailsById(id);   // ← NEW
        model.addAttribute("recipe", recipe);
        return "recipeDetails";
    }

}
