package com.awbd.cookbase.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@Setter
@Getter
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

    @ManyToMany(cascade = CascadeType.MERGE)          //  ← add MERGE (NOT PERSIST!)
    @JoinTable(name = "recipe_category",
            joinColumns        = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps;
}
