package com.backend.todo.config;

import com.backend.todo.TodoApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * @author Alexey Voronin.
 * @since 29.12.2022.
 */
@EnableJpaAuditing
@EnableEnversRepositories(basePackageClasses = TodoApplication.class)
@Configuration
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        // SecurityContext -> getUser -> getEmail.
        return () -> Optional.of("admin");
    }
}
