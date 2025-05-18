package com.awbd.cookbase.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;
    private int prepTimeInMinutes;
    private int cookTimeInMinutes;
    private int servings;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToOne
    private User author;

    @ManyToMany(mappedBy = "recipes")
    private List<Category> categories;

    @OneToMany(mappedBy = "recipe")
    private List<Review> reviews;

    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe")
    private List<Step> steps;
}
