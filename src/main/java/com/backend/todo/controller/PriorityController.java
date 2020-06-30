package com.backend.todo.controller;

import com.backend.todo.domain.Priority;
import com.backend.todo.service.PriorityService;
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
}
