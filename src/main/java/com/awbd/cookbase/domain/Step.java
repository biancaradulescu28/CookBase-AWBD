package com.awbd.cookbase.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stepNumber;

    @Column(length = 1000)
    private String instruction;

    @ManyToOne
    private Recipe recipe;


}
