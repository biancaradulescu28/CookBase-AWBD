package com.awbd.cookbase.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String role;

    @OneToOne(mappedBy = "user")
    private UserProfile profile;

    @OneToMany(mappedBy = "author")
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "reviewer")
    private List<Review> reviews;
}
