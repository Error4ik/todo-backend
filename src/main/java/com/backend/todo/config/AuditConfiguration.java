package com.backend.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 29.12.2022.
 */
@EnableJpaAuditing
@Configuration
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        // SecurityContext -> getUser -> getEmail.
        return () -> Optional.of("admin");
    }
}
