package com.awbd.cookbase.services;

import com.awbd.cookbase.domain.Ingredient;
import com.awbd.cookbase.dtos.IngredientDTO;
import com.awbd.cookbase.exceptions.ResourceNotFoundException;
import com.awbd.cookbase.mappers.IngredientMapper;
import com.awbd.cookbase.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository repo;
    private final IngredientMapper     mapper;

    public IngredientServiceImpl(IngredientRepository repo,
                                 IngredientMapper mapper) {
        this.repo   = repo;
        this.mapper = mapper;
    }

    @Override
    public List<IngredientDTO> findAll() {
        List<Ingredient> list = new LinkedList<>();
        repo.findAll().forEach(list::add);
        return list.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public IngredientDTO findById(Long id) {
        Ingredient ing = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ingredient " + id + " not found"));
        return mapper.toDto(ing);
    }

    @Override
    public IngredientDTO save(IngredientDTO dto) {
        Ingredient saved = repo.save(mapper.toIngredient(dto));
        return mapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
