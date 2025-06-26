package com.awbd.cookbase.controllers;

import com.awbd.cookbase.domain.Authority;
import com.awbd.cookbase.domain.User;
import com.awbd.cookbase.dtos.RegisterDTO;
import com.awbd.cookbase.repositories.AuthorityRepository;
import com.awbd.cookbase.repositories.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "register";
    }

    @PostMapping
    public String processRegister(@ModelAttribute RegisterDTO registerDTO, Model model) {
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match!");
            return "register";
        }

        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }

        Authority guestRole = authorityRepository.findByRole("ROLE_GUEST");

        User newUser = User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
//                .authorities(Collections.singleton(guestRole))
                .authority(guestRole)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        userRepository.save(newUser);
        return "redirect:/login";
    }


}
