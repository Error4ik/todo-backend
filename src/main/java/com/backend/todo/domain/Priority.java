package com.backend.todo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Alexey Voronin.
 * @since 26.06.2020.
 */
@Entity(name = "priorities")
public class Priority {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private String color;

    public Priority() {
    }

    public Priority(String title, String color) {
        this.title = title;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Priority)) return false;
        Priority priority = (Priority) o;
        return getId().equals(priority.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
