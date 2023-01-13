package com.backend.todo.controller;

import com.backend.todo.dto.PriorityCreateEditDto;
import com.backend.todo.dto.PriorityReadDto;
import com.backend.todo.service.PriorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    private Logger logger = LoggerFactory.getLogger(PriorityController.class);

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
        logger.info(String.format("Input arguments: %s", priorityCreateEditDto));
        PriorityReadDto priorityReadDto = priorityService.create(priorityCreateEditDto);
        logger.info(String.format("Save: %s", priorityReadDto));
        return new ResponseEntity<>(priorityReadDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriorityReadDto> update(
            @PathVariable UUID id,
            @RequestBody @Validated PriorityCreateEditDto priorityCreateEditDto) {

        logger.info(String.format("Input arguments: %s", priorityCreateEditDto));
        PriorityReadDto priorityReadDto = priorityService.update(id, priorityCreateEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        logger.info(String.format("Update: %s", priorityReadDto));
        return ok(priorityReadDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriorityReadDto> findById(@PathVariable UUID id) {
        logger.info(String.format("Input arguments: %s", id));
        PriorityReadDto priorityReadDto = priorityService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        logger.info(String.format("Return: %s", priorityReadDto));
        return ok(priorityReadDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        logger.info(String.format("Input arguments: %s", id));
        return priorityService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
