package com.awbd.cookbase.dtos;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDTO {
    private Long   id;
    private String quantity;
    private String name;
}
