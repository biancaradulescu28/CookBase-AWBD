package com.awbd.cookbase.controllers;

import com.awbd.cookbase.domain.Recipe;
import com.awbd.cookbase.dtos.CategoryDTO;
import com.awbd.cookbase.dtos.RecipeDTO;
import com.awbd.cookbase.services.CategoryService;
import com.awbd.cookbase.services.IngredientService;
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
    IngredientService ingredientService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
        this.ingredientService = ingredientService;

    }

    @RequestMapping({"", "/"})
    public String viewRecipes(Model model) {

        String catIdParam =
                ((org.springframework.web.context.request.ServletRequestAttributes)
                        org.springframework.web.context.request.RequestContextHolder
                                .getRequestAttributes())
                        .getRequest()
                        .getParameter("catId");

        List<RecipeDTO> recipes = (catIdParam == null || catIdParam.isBlank())
                ? recipeService.findAll()
                : recipeService.findAllByCategory(Long.valueOf(catIdParam));

        model.addAttribute("recipes", recipes);
        model.addAttribute("categoriesAll", categoryService.findAll());


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
        RecipeDTO recipe = recipeService.findDetailsById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredientsAll", ingredientService.findAll());
        return "recipeDetails";
    }

    @PostMapping("/{id}/ingredients")
    public String addIngredient(@PathVariable Long id,
                                @RequestParam(required = false) Long ingredientId,
                                @RequestParam(required = false) String newName,
                                @RequestParam(required = false) String quantity) {

        recipeService.addIngredient(id, ingredientId, newName, quantity);
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/steps")
    public String addStep(@PathVariable Long id,
                          @RequestParam int stepNumber,
                          @RequestParam String instruction) {
        recipeService.addStep(id, stepNumber, instruction);
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/reviews")
    public String addReview(@PathVariable Long id,
                            @RequestParam int rating,
                            @RequestParam String comment) {
        recipeService.addReview(id, rating, comment);
        return "redirect:/recipes/" + id;
    }


    @RequestMapping("/form")
    public String recipeForm(Model model) {
        RecipeDTO recipe = new RecipeDTO();
        model.addAttribute("recipe",  recipe);
        List<CategoryDTO> categoriesAll = categoryService.findAll();
        model.addAttribute("categoriesAll", categoriesAll );
        return "recipeForm";
    }

}
