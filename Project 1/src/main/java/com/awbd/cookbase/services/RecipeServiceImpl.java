package com.awbd.cookbase.services;

import com.awbd.cookbase.domain.*;
import com.awbd.cookbase.dtos.*;
import com.awbd.cookbase.mappers.*;
import com.awbd.cookbase.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository   recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final StepRepository stepRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper        modelMapper;
    private final CategoryRepository categoryRepository;
    private final IngredientMapper   ingredientMapper;
    private final StepMapper         stepMapper;
    private final ReviewMapper       reviewMapper;

    public RecipeServiceImpl(RecipeRepository   recipeRepository,
                             ModelMapper        modelMapper,
                             IngredientRepository ingredientRepository,
                             StepRepository stepRepository,
                             ReviewRepository reviewRepository,
                             CategoryRepository categoryRepository,
                             IngredientMapper   ingredientMapper,
                             StepMapper         stepMapper,
                             ReviewMapper       reviewMapper) {

        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.stepRepository = stepRepository;
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.ingredientMapper = ingredientMapper;
        this.stepMapper = stepMapper;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public List<RecipeDTO> findAll() {
        var src = recipeRepository.findAllWithCategories(Sort.by("title"));
        return StreamSupport.stream(src.spliterator(), false)
                .map(this::toDtoSummary)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDTO findById(Long id) {
        var entity = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        return toDtoSummary(entity);
    }

    @Override
    public RecipeDTO findDetailsById(Long id) {
        var entity = recipeRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        entity.getIngredients().size();
        entity.getSteps().size();
        entity.getReviews().size();
        return toDtoFull(entity);
    }

    @Override
    public RecipeDTO save(RecipeDTO dto) {
        Recipe entity = modelMapper.map(dto, Recipe.class);


        if (dto.getCategories() != null && !dto.getCategories().isEmpty()) {
            List<Category> managed = dto.getCategories().stream()
                    .map(Long::valueOf)
                    .map(id -> categoryRepository.findById(id)
                            .orElseThrow(() ->
                                    new EntityNotFoundException("Category " + id)))
                    .collect(Collectors.toList());
            entity.setCategories(managed);
        } else {
            entity.setCategories(List.of());
        }


        Recipe saved = recipeRepository.save(entity);

        return modelMapper.map(saved, RecipeDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    private RecipeDTO toDtoSummary(Recipe r) {
        var dto = modelMapper.map(r, RecipeDTO.class);
        dto.setCategories(r.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toList()));
        return dto;
    }

    private RecipeDTO toDtoFull(Recipe r) {
        var dto = toDtoSummary(r);

        dto.setIngredients(r.getIngredients().stream()
                .map(ingredientMapper::toDto)
                .collect(Collectors.toList()));

        dto.setSteps(r.getSteps().stream()
                .sorted(Comparator.comparingInt(Step::getStepNumber))
                .map(stepMapper::toDto)
                .collect(Collectors.toList()));

        dto.setReviews(r.getReviews().stream()
                .sorted(Comparator.comparing(Review::getCreatedAt).reversed())
                .map(reviewMapper::toDto)
                .collect(Collectors.toList()));

        return dto;
    }

    public List<RecipeDTO> findAllByCategory(Long categoryId) {
        return recipeRepository.findAllByCategoryId(categoryId).stream()
                .map(this::toDtoSummary)
                .toList();
    }

    @Override
    public void addIngredient(Long recipeId,
                              Long ingredientId,
                              String newName,
                              String quantity) {

        Recipe recipe = recipeRepository.findByIdWithDetails(recipeId)
                .orElseThrow(() -> new EntityNotFoundException(recipeId.toString()));

        Ingredient ing;

        if (ingredientId != null) {
            ing = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new EntityNotFoundException(ingredientId.toString()));

            if (quantity != null && !quantity.isBlank()) {
                ing.setQuantity(quantity);
            }
        } else {
            ing = ingredientRepository.findByName(newName)
                    .orElseGet(() -> {
                        Ingredient n = new Ingredient();
                        n.setName(newName);
                        return ingredientRepository.save(n);
                    });
            ing.setQuantity(quantity);
        }

        ing.setRecipe(recipe);
        recipe.getIngredients().add(ing);
        log.info("Ingredient '{}' attached to recipe '{}'", ing.getName(), recipe.getTitle());
    }

    @Override
    public void addStep(Long recipeId, int stepNumber, String instruction) {
        Recipe recipe = recipeRepository.findByIdWithDetails(recipeId)
                .orElseThrow(() -> new EntityNotFoundException(recipeId.toString()));

        if (stepNumber == 0) {
            stepNumber = recipe.getSteps().stream()
                    .mapToInt(Step::getStepNumber)
                    .max().orElse(0) + 1;
        }

        Step step = new Step();
        step.setStepNumber(stepNumber);
        step.setInstruction(instruction);
        step.setRecipe(recipe);
        stepRepository.save(step);
    }

    @Override
    public void addReview(Long recipeId, int rating, String comment) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException(recipeId.toString()));

        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(java.time.LocalDateTime.now());
        review.setRecipe(recipe);
        reviewRepository.save(review);
    }



}
