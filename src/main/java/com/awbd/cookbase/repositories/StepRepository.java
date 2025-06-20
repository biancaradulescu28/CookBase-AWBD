package com.awbd.cookbase.repositories;
import com.awbd.cookbase.domain.Step;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends CrudRepository<Step, Long> {
}
