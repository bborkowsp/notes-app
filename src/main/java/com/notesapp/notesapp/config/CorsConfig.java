package com.notesapp.notesapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    private static final List<String> ALLOW_ALL_WILDCARD = List.of("*");
    private static final String MATCH_ALL_PATTERN = "/**";

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final var corsConfiguration = createCorsConfiguration();
        return createCorsConfigurationSource(corsConfiguration);
    }

    private CorsConfiguration createCorsConfiguration() {
        final var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(ALLOW_ALL_WILDCARD);
        corsConfiguration.setAllowedMethods(ALLOW_ALL_WILDCARD);
        corsConfiguration.setAllowedHeaders(ALLOW_ALL_WILDCARD);
        return corsConfiguration;
    }

    private CorsConfigurationSource createCorsConfigurationSource(CorsConfiguration corsConfiguration) {
        final var corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration(MATCH_ALL_PATTERN, corsConfiguration);
        return corsConfigurationSource;
    }

}

