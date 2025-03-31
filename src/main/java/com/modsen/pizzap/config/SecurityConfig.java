package com.modsen.pizzap.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("api/v1/auth/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "api/v1/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "api/v1/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/v1/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/v1/category/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "api/v1/order/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/v1/order/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/v1/order/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/v1/order/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/v1/order/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "api/v1/product/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "api/v1/product/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/v1/product/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/v1/product/**").permitAll()

                        .requestMatchers("api/v1/user/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                .build();
    }
}
