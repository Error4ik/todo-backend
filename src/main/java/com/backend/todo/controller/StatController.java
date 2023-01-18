package com.backend.todo.controller;

import com.backend.todo.dto.StatReadDto;
import com.backend.todo.service.StatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Alexey Voronin.
 * @since 03.07.2020.
 */
@RestController
@RequestMapping("/api/v1/stats")
public class StatController {

    private final StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping
    public ResponseEntity<List<StatReadDto>> findAll() {
        return ok(this.statService.findAll());
    }
}
