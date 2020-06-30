package com.backend.todo.repository;

import com.backend.todo.domain.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@Repository
public interface StatRepository extends JpaRepository<Stat, UUID> {
}
