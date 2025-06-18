package com.awbd.cookbase.services;

import com.awbd.cookbase.exceptions.ResourceNotFoundException;
import com.awbd.cookbase.domain.Category;
import com.awbd.cookbase.domain.Recipe;
import com.awbd.cookbase.dtos.RecipeDTO;
import com.awbd.cookbase.repositories.CategoryRepository;
import com.awbd.cookbase.repositories.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<RecipeDTO> findAll() {

        return StreamSupport
                .stream(recipeRepository
                        .findAllWithCategories(Sort.by("id"))
                        .spliterator(), false)
                .map(r -> {
                    RecipeDTO dto = modelMapper.map(r, RecipeDTO.class);

                    // convert List<Category> → List<String>
                    dto.setCategories(
                            r.getCategories()
                                    .stream()
                                    .map(Category::getName)
                                    .toList());

                    return dto;
                })
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public RecipeDTO findById(Long l) {
        Optional<Recipe> productOptional = recipeRepository.findById(l);
        if (!productOptional.isPresent()) {
            throw new ResourceNotFoundException("product " + l + " not found");
            //throw new RuntimeException("Product not found!");
        }
        return modelMapper.map(productOptional.get(), RecipeDTO.class);
    }

    @Override
    public RecipeDTO save(RecipeDTO dto) {

        // 1. convert DTO ➜ entity (as you already did)
        Recipe recipe = modelMapper.map(dto, Recipe.class);

    /* 2. Replace the detached Category objects with managed ones.
          (If dto carries ids NULL, they’ll be ignored.)               */
        if (recipe.getCategories() != null) {
            List<Category> managed =
                    recipe.getCategories()
                            .stream()
                            .filter(c -> c.getId() != null)          // ignore new / empty
                            .map(c -> categoryRepository.findById(c.getId())
                                    .orElseThrow(() ->
                                            new ResourceNotFoundException(
                                                    "Category " + c.getId() + " not found")))
                            .toList();

            recipe.setCategories(managed);
        }

        Recipe saved = recipeRepository.save(recipe);
        return modelMapper.map(saved, RecipeDTO.class);
    }

}
