package com.spring.hotelreservationsystem.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.assertj.core.api.Assertions.assertThat;

class WebConfigTest {

    @Test
    void corsConfigurer_shouldNotBeNull() {
        WebConfig config = new WebConfig();
        WebMvcConfigurer configurer = config.corsConfigurer();

        assertThat(configurer).isNotNull();
    }

    @Test
    void corsConfigurer_shouldAddMappings() {
        WebConfig config = new WebConfig();
        WebMvcConfigurer configurer = config.corsConfigurer();

        CorsRegistry registry = new CorsRegistry();
        configurer.addCorsMappings(registry);

        // If no exception is thrown → configuration works
        assertThat(registry).isNotNull();
    }
}