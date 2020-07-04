package com.backend.todo.search;

import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 04.07.2020.
 */
public class SearchParams {

    private String title;
    private int completed;
    private UUID priority;
    private UUID category;

    public SearchParams() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public UUID getPriority() {
        return priority;
    }

    public void setPriority(UUID priority) {
        this.priority = priority;
    }

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SearchParams{" +
                "title='" + title + '\'' +
                ", completed=" + completed +
                ", priority=" + priority +
                ", category=" + category +
                '}';
    }
}
