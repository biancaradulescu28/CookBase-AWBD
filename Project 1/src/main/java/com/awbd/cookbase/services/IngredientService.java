package com.awbd.cookbase.services;

import com.awbd.cookbase.dtos.IngredientDTO;
import java.util.List;

public interface IngredientService {
    List<IngredientDTO> findAll();
    IngredientDTO findById(Long id);
    IngredientDTO save(IngredientDTO dto);
    void deleteById(Long id);
}
