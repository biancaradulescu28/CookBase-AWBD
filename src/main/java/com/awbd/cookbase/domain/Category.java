package com.awbd.cookbase.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name="category_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name="recipe_id", referencedColumnName = "id"))
    private List<Recipe> recipes;
}
