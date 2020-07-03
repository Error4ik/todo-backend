package com.backend.todo.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Alexey Voronin.
 * @since 26.06.2020.
 */
@Entity(name = "categories")
public class Category {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    @Column(name = "completed_count")
    private int completedCount;

    @Column(name = "uncompleted_count")
    private int uncompletedCount;

    public Category() {
    }

    public Category(String title, int completedCount, int uncompletedCount) {
        this.title = title;
        this.completedCount = completedCount;
        this.uncompletedCount = uncompletedCount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public int getUncompletedCount() {
        return uncompletedCount;
    }

    public void setUncompletedCount(int uncompletedCount) {
        this.uncompletedCount = uncompletedCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return getId().equals(category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.format("Category {id = %s, title = %s}", getId(), getTitle());
    }
}
