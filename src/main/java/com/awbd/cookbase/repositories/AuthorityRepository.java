package com.awbd.cookbase.repositories;

import com.awbd.cookbase.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
