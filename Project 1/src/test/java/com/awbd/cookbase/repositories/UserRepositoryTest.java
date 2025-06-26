package com.awbd.cookbase.repositories;

import com.awbd.cookbase.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("h2")
@Slf4j
public class UserRepositoryTest {

    UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void findByUsername_shouldReturnUser() {

        Optional<User> userOpt = userRepository.findByUsername("user1");

        assertFalse(userOpt.isEmpty());
        log.info("Found imported user: {}", userOpt.get().getUsername());
    }

}
