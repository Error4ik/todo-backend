package com.backend.todo.integration.service;

import com.backend.todo.dto.PriorityCreateEditDto;
import com.backend.todo.dto.PriorityReadDto;
import com.backend.todo.integration.IntegrationBaseTest;
import com.backend.todo.service.PriorityService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexey Voronin.
 * @since 07.12.2022.
 */
@RequiredArgsConstructor
public class PriorityServiceIT extends IntegrationBaseTest {

    private final UUID PRIORITY_ID = UUID.fromString("b6a16ada-9d8a-407d-b4da-271d835156b8");

    private final PriorityService priorityService;

    @Test
    public void findAll() {
        List<PriorityReadDto> actualResult = priorityService.findAllByOrderByTitleAsc();

        assertThat(actualResult).hasSize(3);
    }

    @Test
    public void create() {
        PriorityCreateEditDto newPriority = new PriorityCreateEditDto("new priority", "some color");

        PriorityReadDto actualValue = priorityService.create(newPriority);

        assertNotNull(actualValue.getId());
        assertEquals(newPriority.getTitle(), actualValue.getTitle());
    }

    @Test
    public void findById() {
        Optional<PriorityReadDto> actualResult = priorityService.findById(PRIORITY_ID);

        assertTrue(actualResult.isPresent());

        actualResult.ifPresent(actual -> assertEquals("Normal", actual.getTitle()));
    }

    @Test
    public void delete() {
        PriorityCreateEditDto newPriority = new PriorityCreateEditDto("new priority", "some color");
        PriorityReadDto actualValue = priorityService.create(newPriority);

        assertNotNull(actualValue.getId());
        assertTrue(priorityService.delete(actualValue.getId()));
        assertFalse(priorityService.delete(UUID.randomUUID()));
    }

    @Test
    public void update() {
        PriorityCreateEditDto newPriority = new PriorityCreateEditDto("other", "some color");

        Optional<PriorityReadDto> actualValue = priorityService.update(PRIORITY_ID, newPriority);

        assertTrue(actualValue.isPresent());
        actualValue.ifPresent(actual -> assertEquals(newPriority.getTitle(), actual.getTitle()));
    }
}
