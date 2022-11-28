package com.backend.todo.domain;

import lombok.*;

import javax.persistence.*;
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
}
