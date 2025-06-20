/* ───────────────── IngredientMapper ───────────────── */
package com.awbd.cookbase.mappers;

import com.awbd.cookbase.domain.Ingredient;
import com.awbd.cookbase.dtos.IngredientDTO;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public IngredientDTO toDto(Ingredient ingredient) {
        Long id = ingredient.getId();
        String name = ingredient.getName();
        String quantity = ingredient.getQuantity();
        return new IngredientDTO(id, name, quantity);
    }

    public Ingredient toIngredient(IngredientDTO dto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(dto.getId());
        ingredient.setName(dto.getName());
        ingredient.setQuantity(dto.getQuantity());
        return ingredient;
    }
}
