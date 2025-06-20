package com.awbd.cookbase.services;

import com.awbd.cookbase.dtos.ReviewDTO;
import java.util.List;

public interface ReviewService {
    List<ReviewDTO> findAll();
    ReviewDTO findById(Long id);
    ReviewDTO save(ReviewDTO dto);
    void deleteById(Long id);
}
