package com.backend.todo.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 26.06.2020.
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tasks")
public class Task {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private int completed;

    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "priority")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;
}
