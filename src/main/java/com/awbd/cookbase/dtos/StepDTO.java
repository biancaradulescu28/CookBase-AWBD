package com.awbd.cookbase.dtos;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StepDTO {
    private Long   id;
    private int    stepNumber;
    private String instruction;
}
