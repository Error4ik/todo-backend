package com.backend.todo.integration.service;

import com.backend.todo.dto.StatReadDto;
import com.backend.todo.integration.IntegrationBaseTest;
import com.backend.todo.service.StatService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alexey Voronin.
 * @since 07.12.2022.
 */
@RequiredArgsConstructor
public class StatServiceIT extends IntegrationBaseTest {

    private final StatService statService;

    @Test
    public void findAll() {
        final int completedTotal = 0;
        final int uncompletedTotal = 5;
        List<StatReadDto> actualResult = statService.findAll();

        assertThat(actualResult).hasSize(1);
        assertEquals(completedTotal, actualResult.get(0).getCompletedTotal());
        assertEquals(uncompletedTotal, actualResult.get(0).getUncompletedTotal());
    }
}
