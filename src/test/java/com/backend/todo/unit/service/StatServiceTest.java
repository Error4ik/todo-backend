package com.backend.todo.unit.service;

import com.backend.todo.domain.Stat;
import com.backend.todo.dto.StatReadDto;
import com.backend.todo.mapper.StatReadMapper;
import com.backend.todo.repository.StatRepository;
import com.backend.todo.service.StatService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Alexey Voronin.
 * @since 03.12.2022.
 */
@ExtendWith(MockitoExtension.class)
public class StatServiceTest {

    private final UUID STAT_ID = UUID.randomUUID();

    private final Stat STAT = new Stat(
            STAT_ID,
            0,
            0
    );

    @Mock
    private StatRepository statRepository;
    @Mock
    private StatReadMapper statReadMapper;

    @InjectMocks
    private StatService statService;

    @Test
    public void findAll() {
        when(statRepository.findAll()).thenReturn(Lists.newArrayList(STAT));

        List<StatReadDto> result = statService.findAll();

        assertThat(result).hasSize(1);
        verify(statRepository, times(1)).findAll();
    }
}
