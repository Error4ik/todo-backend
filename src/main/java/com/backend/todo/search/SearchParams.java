package com.backend.todo.search;

import lombok.*;

import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 04.07.2020.
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchParams {

    private String title;
    private Integer completed;
    private UUID priority;
    private UUID category;
    private Integer pageNumber;
    private Integer pageLimit;
    private String sortColumn;
    private String sortDirection;
}
