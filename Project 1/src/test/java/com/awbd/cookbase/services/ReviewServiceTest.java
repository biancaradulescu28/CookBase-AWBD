package com.awbd.cookbase.services;

import com.awbd.cookbase.domain.Review;
import com.awbd.cookbase.dtos.ReviewDTO;
import com.awbd.cookbase.exceptions.ResourceNotFoundException;
import com.awbd.cookbase.mappers.ReviewMapper;
import com.awbd.cookbase.repositories.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    public void findAll_shouldReturnMappedDTOs() {
        Review review = new Review();
        review.setId(1L);
        review.setRating(5);
        review.setComment("Great!");
        review.setCreatedAt(LocalDateTime.now());

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(1L);
        reviewDTO.setRating(5);
        reviewDTO.setComment("Great!");

        when(reviewRepository.findAll()).thenReturn(List.of(review));
        when(reviewMapper.toDto(review)).thenReturn(reviewDTO);

        List<ReviewDTO> result = reviewService.findAll();

        assertEquals(1, result.size());
        assertEquals("Great!", result.get(0).getComment());

        verify(reviewRepository, times(1)).findAll();
        verify(reviewMapper, times(1)).toDto(review);
    }

    @Test
    public void findById_shouldReturnDTO() {
        Review review = new Review();
        review.setId(2L);

        ReviewDTO dto = new ReviewDTO();
        dto.setId(2L);

        when(reviewRepository.findById(2L)).thenReturn(Optional.of(review));
        when(reviewMapper.toDto(review)).thenReturn(dto);

        ReviewDTO result = reviewService.findById(2L);

        assertEquals(2L, result.getId());
        verify(reviewRepository).findById(2L);
        verify(reviewMapper).toDto(review);
    }

    @Test
    public void findById_shouldThrow_whenNotFound() {
        when(reviewRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reviewService.findById(99L));
        verify(reviewRepository).findById(99L);
    }

    @Test
    public void save_shouldReturnSavedDTO() {
        ReviewDTO dto = new ReviewDTO();
        dto.setRating(4);
        dto.setComment("Nice");

        Review entity = new Review();
        entity.setRating(4);
        entity.setComment("Nice");

        Review saved = new Review();
        saved.setId(3L);
        saved.setRating(4);
        saved.setComment("Nice");

        ReviewDTO resultDto = new ReviewDTO();
        resultDto.setId(3L);
        resultDto.setRating(4);
        resultDto.setComment("Nice");

        when(reviewMapper.toReview(dto)).thenReturn(entity);
        when(reviewRepository.save(entity)).thenReturn(saved);
        when(reviewMapper.toDto(saved)).thenReturn(resultDto);

        ReviewDTO result = reviewService.save(dto);

        assertEquals(3L, result.getId());
        verify(reviewRepository).save(entity);
        verify(reviewMapper).toReview(dto);
        verify(reviewMapper).toDto(saved);
    }

    @Test
    public void deleteById_shouldCallRepository() {
        reviewService.deleteById(5L);
        verify(reviewRepository, times(1)).deleteById(5L);
    }
}
