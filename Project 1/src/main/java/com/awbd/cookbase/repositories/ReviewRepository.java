package com.awbd.cookbase.repositories;

import com.awbd.cookbase.domain.Category;
import com.awbd.cookbase.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
}
