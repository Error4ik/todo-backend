package com.backend.todo.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Alexey Voronin.
 * @since 26.06.2020.
 */
@Entity(name = "statistics")
public class Stat {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "completed_total")
    private int completedTotal;

    @Column(name = "uncompleted_total")
    private int uncompletedTotal;

    public Stat() {
    }

    public Stat(int completedTotal, int uncompletedTotal) {
        this.completedTotal = completedTotal;
        this.uncompletedTotal = uncompletedTotal;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getCompletedTotal() {
        return completedTotal;
    }

    public void setCompletedTotal(int completedTotal) {
        this.completedTotal = completedTotal;
    }

    public int getUncompletedTotal() {
        return uncompletedTotal;
    }

    public void setUncompletedTotal(int uncompletedTotal) {
        this.uncompletedTotal = uncompletedTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stat)) return false;
        Stat stat = (Stat) o;
        return getId().equals(stat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
