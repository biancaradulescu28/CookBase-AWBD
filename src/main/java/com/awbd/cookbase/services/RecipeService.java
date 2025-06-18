package com.awbd.cookbase.services;

import com.awbd.cookbase.dtos.RecipeDTO;


import java.util.List;
import java.util.Optional;


public interface RecipeService {

    List<RecipeDTO> findAll();
    void deleteById(Long id);
    RecipeDTO findById(Long l);
    RecipeDTO save(RecipeDTO recipe);
}
