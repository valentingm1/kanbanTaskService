package com.example.task_service.dto.entrada;

import com.example.task_service.entity.Task;

import java.util.Set;

public class TaskEntradaDTO {

    private String title;
    private String nominalTime;
    private Set<Long> dependencyIds;
    private Set<Long> assigneeIds;


    public TaskEntradaDTO(String title, String nominalTime, Set<Long> dependencyIds, Set<Long> assigneeIds) {
        this.title = title;
        this.nominalTime = nominalTime;
        this.dependencyIds = dependencyIds;
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

    public Set<Long> getDependencyIds() {
        return dependencyIds;
    }

    public void setDependencyIds(Set<Long> dependencyIds) {
        this.dependencyIds = dependencyIds;
    }

    public Set<Long> getAssigneeIds() {
        return assigneeIds;
    }

    public void setAssigneeIds(Set<Long> assigneeIds) {
        this.assigneeIds = assigneeIds;
    }
}
