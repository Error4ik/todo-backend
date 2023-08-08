package com.backend.todo.unit.service;

import com.backend.todo.domain.Priority;
import com.backend.todo.dto.PriorityCreateEditDto;
import com.backend.todo.dto.PriorityReadDto;
import com.backend.todo.mapper.PriorityCreateEditMapper;
import com.backend.todo.mapper.PriorityReadMapper;
import com.backend.todo.repository.PriorityRepository;
import com.backend.todo.service.PriorityService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Alexey Voronin.
 * @since 03.12.2022.
 */
@ExtendWith(MockitoExtension.class)
public class PriorityServiceTest {

    private final UUID PRIORITY_ID = UUID.randomUUID();

    private final Priority PRIORITY = new Priority(
            PRIORITY_ID,
            "test",
            "test"
    );

    @Mock
    private PriorityRepository priorityRepository;
    @Mock
    private PriorityCreateEditMapper priorityCreateEditMapper;
    @Mock
    private PriorityReadMapper priorityReadMapper;

    @InjectMocks
    public PriorityService priorityService;

    @Test
    public void findAll() {
        when(priorityRepository.findAllByOrderByTitleAsc()).thenReturn(Lists.newArrayList(PRIORITY));

        List<PriorityReadDto> result = priorityService.findAllByOrderByTitleAsc();

        assertThat(result).hasSize(1);
        verify(priorityRepository, times(1)).findAllByOrderByTitleAsc();
    }

    @Test
    public void create() {
        when(priorityCreateEditMapper.map(any())).thenReturn(PRIORITY);
        when(priorityRepository.save(any())).thenReturn(PRIORITY);
        when(priorityReadMapper.map(any())).
                thenReturn(new PriorityReadDto(PRIORITY_ID, "test", "test"));

        PriorityCreateEditDto priorityCreateEditDto =
                new PriorityCreateEditDto("test", "test");
        PriorityReadDto priorityReadDto = priorityService.create(priorityCreateEditDto);

        assertEquals(PRIORITY_ID, priorityReadDto.getId());
        verify(priorityRepository, times(1)).save(any());
        verify(priorityCreateEditMapper, times(1)).map(any());
        verify(priorityReadMapper, times(1)).map(any());
    }

    @Test
    public void findById() {
        when(priorityRepository.findById(any())).thenReturn(Optional.of(PRIORITY));
        when(priorityReadMapper.map(any()))
                .thenReturn(new PriorityReadDto(PRIORITY_ID, "test", "test"));

        Optional<PriorityReadDto> actualResult = priorityService.findById(PRIORITY_ID);

        actualResult.ifPresent(actual -> assertEquals(PRIORITY_ID, actual.getId()));
        verify(priorityRepository, times(1)).findById(any());
        verify(priorityReadMapper, times(1)).map(any());
    }

    @Test
    public void deleteWithTheCorrectID() {
        when(priorityRepository.findById(any())).thenReturn(Optional.of(PRIORITY));

        boolean actualResult = priorityService.delete(PRIORITY_ID);
        assertTrue(actualResult);

        verify(priorityRepository, times(1)).findById(any());
        verify(priorityRepository, times(1)).delete(any());
    }

    @Test
    public void deleteWithTheInCorrectID() {
        when(priorityRepository.findById(any())).thenReturn(Optional.empty());

        boolean actualResult = priorityService.delete(PRIORITY_ID);
        assertFalse(actualResult);

        verify(priorityRepository, times(1)).findById(any());
    }

    @Test
    public void update() {
        when(priorityRepository.findById(any())).thenReturn(Optional.of(PRIORITY));
        when(priorityCreateEditMapper.map(any(), any())).thenReturn(PRIORITY);
        when(priorityRepository.saveAndFlush(any())).thenReturn(PRIORITY);
        when(priorityReadMapper.map(any())).
                thenReturn(new PriorityReadDto(PRIORITY_ID, "test", "test"));

        PriorityCreateEditDto priorityCreateEditDto =
                new PriorityCreateEditDto("test", "test");
        Optional<PriorityReadDto> actualResult = priorityService.update(PRIORITY_ID, priorityCreateEditDto);

        actualResult.ifPresent(result -> assertEquals(PRIORITY_ID, result.getId()));
        verify(priorityRepository, times(1)).findById(any());
        verify(priorityCreateEditMapper, times(1)).map(any(), any());
        verify(priorityRepository, times(1)).saveAndFlush(any());
        verify(priorityReadMapper, times(1)).map(any());
    }
}
