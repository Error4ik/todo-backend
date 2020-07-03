package com.backend.todo.controller;

import com.backend.todo.domain.Priority;
import com.backend.todo.service.PriorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@RestController
@RequestMapping("/priority")
public class PriorityController {

    private Logger logger = LoggerFactory.getLogger(PriorityController.class);

    private PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @RequestMapping("/priorities")
    public List<Priority> getPriorities() {
        return this.priorityService.findAllByOrderByTitleAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> addPriority(@NonNull Priority priority) {
        logger.info(String.format("Input arguments: %s", priority));
        if (priority.getId() != null) {
            logger.info("The redundant param: id parameter must be null");
            return new ResponseEntity("The redundant param: id parameter must be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getTitle() == null || priority.getTitle().trim().isEmpty()) {
            logger.info("Missed parameters: title");
            return new ResponseEntity("Missed parameters: title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getColor() == null || priority.getColor().trim().isEmpty()) {
            logger.info("Missed parameters: color");
            return new ResponseEntity("Missed parameters: color", HttpStatus.NOT_ACCEPTABLE);
        }
        Priority p = this.priorityService.addPriority(priority);
        logger.info(String.format("Save: %s", p));
        return ResponseEntity.ok(p);
    }

    @PutMapping("/update")
    public ResponseEntity<Priority> updatePriority(@NonNull Priority priority) {
        logger.info(String.format("Input arguments: %s", priority));
        if (priority.getId() == null) {
            logger.info("Missed: the id parameter must not be null");
            return new ResponseEntity("Missed: the id parameter must not be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getTitle() == null || priority.getTitle().trim().isEmpty()) {
            logger.info("Missed parameters: title");
            return new ResponseEntity("Missed parameters: title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getColor() == null || priority.getColor().trim().isEmpty()) {
            logger.info("Missed parameters: color");
            return new ResponseEntity("Missed parameters: color", HttpStatus.NOT_ACCEPTABLE);
        }
        Priority p = this.priorityService.updatePriority(priority);
        logger.info(String.format("Update: %s", p));
        return ResponseEntity.ok(p);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> getPriorityById(@PathVariable UUID id) {
        logger.info(String.format("Input arguments: %s", id));
        Optional<Priority> priority = this.priorityService.getPriorityById(id);
        if (!priority.isPresent()) {
            logger.info("There is no entity with this ID!");
            return new ResponseEntity("There is no entity with this ID!", HttpStatus.NOT_FOUND);
        }
        logger.info(String.format("Return: %s", priority.get()));
        return ResponseEntity.ok(priority.get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePriority(@PathVariable UUID id) {
        logger.info(String.format("Input arguments: %s", id));
        try {
            this.priorityService.deletePriority(id);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("There is no entity with this ID!");
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
