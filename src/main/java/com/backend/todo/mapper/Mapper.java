package com.backend.todo.mapper;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 24.11.2022.
 */
public interface Mapper<F, T> {

    T map(F object);

    default T map(F fromObject, T toObject) {
        return toObject;
    }
}
