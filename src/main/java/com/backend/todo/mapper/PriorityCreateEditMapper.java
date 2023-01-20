package com.backend.todo.mapper;

import com.backend.todo.domain.Priority;
import com.backend.todo.dto.PriorityCreateEditDto;
import org.springframework.stereotype.Component;

/**
 * @author Alexey Voronin.
 * @since 27.11.2022.
 */
@Component
public class PriorityCreateEditMapper implements Mapper<PriorityCreateEditDto, Priority> {

    @Override
    public Priority map(PriorityCreateEditDto object) {
        Priority priority = new Priority();
        copy(object, priority);
        return priority;
    }

    @Override
    public Priority map(PriorityCreateEditDto fromObject, Priority toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(PriorityCreateEditDto object, Priority priority) {
        priority.setTitle(object.getTitle());
        priority.setColor(object.getColor());
    }
}
