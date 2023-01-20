package com.backend.todo.controller;

import com.backend.todo.dto.PriorityCreateEditDto;
import com.backend.todo.dto.PriorityReadDto;
import com.backend.todo.exception.NotFoundException;
import com.backend.todo.service.PriorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@RestController
@RequestMapping("/api/v1/priorities")
public class PriorityController {

    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @GetMapping
    public List<PriorityReadDto> findAllByOrderByTitleAsc() {
        return priorityService.findAllByOrderByTitleAsc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PriorityReadDto> create(@RequestBody @Validated PriorityCreateEditDto priorityCreateEditDto) {
        PriorityReadDto priorityReadDto = priorityService.create(priorityCreateEditDto);
        return new ResponseEntity<>(priorityReadDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriorityReadDto> update(
            @PathVariable UUID id,
            @RequestBody @Validated PriorityCreateEditDto priorityCreateEditDto) {

        PriorityReadDto priorityReadDto = priorityService.update(id, priorityCreateEditDto)
                .orElseThrow(() -> new NotFoundException("There is no object with this ID"));

        return ok(priorityReadDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriorityReadDto> findById(@PathVariable UUID id) {
        PriorityReadDto priorityReadDto = priorityService.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no object with this ID"));
        return ok(priorityReadDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return priorityService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
