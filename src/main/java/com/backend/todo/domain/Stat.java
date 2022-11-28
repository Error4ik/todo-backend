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
}
