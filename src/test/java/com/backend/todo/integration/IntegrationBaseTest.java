package com.backend.todo.integration;

import com.backend.todo.integration.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 06.12.2022.
 */
@IT
public class IntegrationBaseTest {

    private final static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.2");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
