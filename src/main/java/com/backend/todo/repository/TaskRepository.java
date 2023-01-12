package com.backend.todo.repository;

import com.backend.todo.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT t FROM tasks t where " +
            "(CAST (:title AS text) IS NULL OR lower(t.title) like lower(concat('%', CAST (:title AS text), '%')))" +
            "and (:completed IS NULL OR t.completed = :completed)" +
            "and (CAST (:priority AS text) IS NULL OR t.priority.id = :priority)" +
            "and (CAST (:category AS text) IS NULL OR t.category.id = :category)")
    Page<Task> searchTaskByParams(
            @Param("title") String title,
            @Param("completed") Integer completed,
            @Param("priority") UUID priority,
            @Param("category") UUID category,
            Pageable pageable);
}
