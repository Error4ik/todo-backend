package com.backend.todo.controller;

import com.backend.todo.domain.Priority;
import com.backend.todo.service.PriorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@RestController
@RequestMapping("/priority")
public class PriorityController {

    private PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @RequestMapping("/priorities")
    public List<Priority> getPriorities() {
        return this.priorityService.getPriorities();
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> addPriority(@NonNull Priority priority) {
        if (priority.getId() != null) {
            return new ResponseEntity("The redundant param: id parameter must be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getTitle() == null || priority.getTitle().trim().isEmpty()) {
            return new ResponseEntity("Missed parameters: title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getColor() == null || priority.getColor().trim().isEmpty()) {
            return new ResponseEntity("Missed parameters: color", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(this.priorityService.addPriority(priority));
    }

    @PutMapping("/update")
    public ResponseEntity<Priority> updatePriority(@NonNull Priority priority) {
        if (priority.getId() == null) {
            return new ResponseEntity("Missed: the id parameter must not be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getTitle() == null || priority.getTitle().trim().isEmpty()) {
            return new ResponseEntity("Missed parameters: title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getColor() == null || priority.getColor().trim().isEmpty()) {
            return new ResponseEntity("Missed parameters: color", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(this.priorityService.updatePriority(priority));
    }
}
