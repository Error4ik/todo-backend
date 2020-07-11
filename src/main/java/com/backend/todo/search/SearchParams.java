package com.backend.todo.search;

import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 04.07.2020.
 */
public class SearchParams {

    private String title;
    private Integer completed;
    private UUID priority;
    private UUID category;
    private Integer pageNumber;
    private Integer pageLimit;
    private String sortColumn;
    private String sortDirection;

    public SearchParams() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
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

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(Integer pageLimit) {
        this.pageLimit = pageLimit;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
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
