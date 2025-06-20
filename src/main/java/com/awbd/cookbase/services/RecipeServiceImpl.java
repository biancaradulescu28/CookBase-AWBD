package com.awbd.cookbase.services;

import com.awbd.cookbase.domain.*;
import com.awbd.cookbase.dtos.*;
import com.awbd.cookbase.mappers.*;
import com.awbd.cookbase.repositories.CategoryRepository;
import com.awbd.cookbase.repositories.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
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
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository   recipeRepository;
    private final ModelMapper        modelMapper;
    private final CategoryRepository categoryRepository;
    private final IngredientMapper   ingredientMapper;
    private final StepMapper         stepMapper;
    private final ReviewMapper       reviewMapper;

    public RecipeServiceImpl(RecipeRepository   recipeRepository,
                             ModelMapper        modelMapper,
                             CategoryRepository categoryRepository,
                             IngredientMapper   ingredientMapper,
                             StepMapper         stepMapper,
                             ReviewMapper       reviewMapper) {

        this.recipeRepository = recipeRepository;
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
        var entity = modelMapper.map(dto, Recipe.class);

        if (entity.getCategories() != null) {
            var managed = entity.getCategories().stream()
                    .filter(c -> c.getId() != null)
                    .map(c -> categoryRepository.findById(c.getId())
                            .orElseThrow(() ->
                                    new EntityNotFoundException(c.getId().toString())))
                    .collect(Collectors.toList());
            entity.setCategories(managed);
        }

        var saved = recipeRepository.save(entity);
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
                .sorted(Comparator.comparing(Review::getCreatedAt))
                .map(reviewMapper::toDto)
                .collect(Collectors.toList()));

        return dto;
    }
}
