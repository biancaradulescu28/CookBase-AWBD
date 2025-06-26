/* ───────────────── ReviewMapper ───────────────── */
package com.awbd.cookbase.mappers;

import com.awbd.cookbase.domain.Review;
import com.awbd.cookbase.dtos.ReviewDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toDto(Review review) {
        Long   id        = review.getId();
        int    rating    = review.getRating();
        String comment   = review.getComment();
        var    createdAt = review.getCreatedAt();
        String reviewer  = review.getReviewer() == null
                ? null
                : review.getReviewer().getUsername();

        return new ReviewDTO(id, rating, comment, createdAt, reviewer);
    }


    public Review toReview(ReviewDTO dto) {
        Review review = new Review();
        review.setId(dto.getId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setCreatedAt(dto.getCreatedAt());


        return review;
    }
}
