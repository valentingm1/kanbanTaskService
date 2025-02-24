package com.example.task_service.dto.entrada;

import com.example.task_service.entity.Task;

import java.util.Set;

public class TaskEntradaDTO {

    private String title;
    private String nominalTime;
    private Set<Task> dependencies;
    private Set<Long> assigneeIds;

    public TaskEntradaDTO(String title, String nominalTime, Set<Task> dependencies, Set<Long> assigneeIds) {
        this.title = title;
        this.nominalTime = nominalTime;
        this.dependencies = dependencies;
        this.assigneeIds = assigneeIds;
    }

    public TaskEntradaDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNominalTime() {
        return nominalTime;
    }

    public void setNominalTime(String nominalTime) {
        this.nominalTime = nominalTime;
    }

    public Set<Task> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<Task> dependencies) {
        this.dependencies = dependencies;
    }

    public Set<Long> getAssigneeIds() {
        return assigneeIds;
    }

    public void setAssigneeIds(Set<Long> assigneeIds) {
        this.assigneeIds = assigneeIds;
    }
}
