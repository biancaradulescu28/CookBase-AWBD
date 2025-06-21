package com.awbd.cookbase.repositories;

import com.awbd.cookbase.domain.Recipe;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {

    Optional<Recipe> findById (Long id);

   Optional<Recipe> findByTitle(String title);

    List<Recipe> findByTitleLike(String titlePattern);


    void deleteById(Long id);
    Recipe save(Recipe recipe);

    @Query("""
       select distinct r
       from Recipe r
       left join fetch r.categories
       """)
    Iterable<Recipe> findAllWithCategories(org.springframework.data.domain.Sort sort);

    @Query("""
   select r
   from Recipe r
   left join fetch r.categories          
   where r.id = :id
   """)
    Optional<Recipe> findByIdWithDetails(@Param("id") Long id);



}

