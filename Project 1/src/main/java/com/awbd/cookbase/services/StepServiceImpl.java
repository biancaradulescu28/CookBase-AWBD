package com.awbd.cookbase.services;

import com.awbd.cookbase.domain.Step;
import com.awbd.cookbase.dtos.StepDTO;
import com.awbd.cookbase.exceptions.ResourceNotFoundException;
import com.awbd.cookbase.mappers.StepMapper;
import com.awbd.cookbase.repositories.StepRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class StepServiceImpl implements StepService {

    private final StepRepository repo;
    private final StepMapper mapper;

    public StepServiceImpl(StepRepository repo, StepMapper mapper) {
        this.repo   = repo;
        this.mapper = mapper;
    }

    @Override
    public List<StepDTO> findAll() {
        List<Step> list = new LinkedList<>();
        repo.findAll().forEach(list::add);
        return list.stream().map(mapper::toDto).toList();
    }

    @Override
    public StepDTO findById(Long id) {
        Step step = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("step " + id + " not found"));
        return mapper.toDto(step);
    }

    @Override
    public StepDTO save(StepDTO dto) {
        Step saved = repo.save(mapper.toStep(dto));
        return mapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}

