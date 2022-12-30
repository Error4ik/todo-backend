package com.backend.todo.repository;

import com.backend.todo.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@Repository
public interface CategoryRepository extends
        JpaRepository<Category, UUID>,
        RevisionRepository<Category, UUID, Integer> {

    @Query("SELECT c FROM categories c where (:title is null or :title='' or lower(c.title)" +
            " like lower(concat('%', :title,'%'))) order by c.title asc")
    List<Category> findByTitle(@Param("title") String title);

    List<Category> findAllByOrderByTitleAsc();
}
