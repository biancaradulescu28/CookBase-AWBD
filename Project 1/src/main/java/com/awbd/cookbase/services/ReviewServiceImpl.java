package com.awbd.cookbase.services;

import com.awbd.cookbase.domain.Review;
import com.awbd.cookbase.dtos.ReviewDTO;
import com.awbd.cookbase.exceptions.ResourceNotFoundException;
import com.awbd.cookbase.mappers.ReviewMapper;
import com.awbd.cookbase.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repo;
    private final ReviewMapper mapper;

    public ReviewServiceImpl(ReviewRepository repo, ReviewMapper mapper) {
        this.repo   = repo;
        this.mapper = mapper;
    }

    @Override
    public List<ReviewDTO> findAll() {
        List<Review> list = new LinkedList<>();
        repo.findAll().forEach(list::add);
        return list.stream().map(mapper::toDto).toList();
    }

    @Override
    public ReviewDTO findById(Long id) {
        Review rev = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("review " + id + " not found"));
        return mapper.toDto(rev);
    }

    @Override
    public ReviewDTO save(ReviewDTO dto) {
        Review saved = repo.save(mapper.toReview(dto));
        return mapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

}
