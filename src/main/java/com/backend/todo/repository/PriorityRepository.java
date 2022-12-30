package com.backend.todo.repository;

import com.backend.todo.domain.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@Repository
public interface PriorityRepository extends
        JpaRepository<Priority, UUID>,
        RevisionRepository<Priority, UUID, Integer> {

    List<Priority> findAllByOrderByTitleAsc();
}
