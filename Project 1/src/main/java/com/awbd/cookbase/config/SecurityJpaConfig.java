package com.awbd.cookbase.config;

import com.awbd.cookbase.services.security.JpaUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
//@Profile("mysql")
@EnableMethodSecurity
public class SecurityJpaConfig {

    private final JpaUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SecurityJpaConfig(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/main", "/login", "/register",
                                "/webjars/**", "/css/**", "/js/**",
                                "/images/**", "/h2-console/**").permitAll()


                        .requestMatchers(HttpMethod.GET, "/recipes/**").hasAnyRole("ADMIN", "GUEST")
                        .requestMatchers(HttpMethod.GET,  "/recipes/form").hasAnyRole("ADMIN","GUEST")
                        .requestMatchers(HttpMethod.POST, "/recipes").hasAnyRole("ADMIN","GUEST")
                        .requestMatchers("/recipes/delete/**").hasRole("ADMIN")

                        .anyRequest().authenticated()

                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                )
                                .userDetailsService(userDetailsService)
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                                .loginProcessingUrl("/perform_login")
                )
                .logout(log -> log
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/access_denied"))
                .httpBasic(Customizer.withDefaults())
                .build();
    }


}
