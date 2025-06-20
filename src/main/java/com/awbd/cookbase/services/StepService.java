package com.awbd.cookbase.services;

import com.awbd.cookbase.dtos.StepDTO;
import java.util.List;

public interface StepService {
    List<StepDTO> findAll();
    StepDTO       findById(Long id);
    StepDTO       save(StepDTO dto);
    void          deleteById(Long id);
}