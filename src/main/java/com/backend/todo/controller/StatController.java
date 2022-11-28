package com.backend.todo.controller;

import com.backend.todo.dto.StatReadDto;
import com.backend.todo.service.StatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

/**
 * @author Alexey Voronin.
 * @since 03.07.2020.
 */
@RestController
@RequestMapping("/stat")
public class StatController {

    private Logger logger = LoggerFactory.getLogger(StatController.class);

    private StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping("/stats")
    public ResponseEntity<List<StatReadDto>> getStats() {
        return ok(this.statService.getStats());
    }
}
