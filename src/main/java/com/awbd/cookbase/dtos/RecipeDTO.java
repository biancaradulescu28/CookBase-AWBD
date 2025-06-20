package com.awbd.cookbase.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class RecipeDTO {

    private Long   id;
    private int    cookTimeInMinutes;
    private int    prepTimeInMinutes;
    private int    servings;
    private String description;
    private String title;
    private List<String>        categories   = new ArrayList<>();
    private List<IngredientDTO> ingredients  = new ArrayList<>();
    private List<StepDTO>       steps        = new ArrayList<>();
    private List<ReviewDTO>     reviews      = new ArrayList<>();


}
