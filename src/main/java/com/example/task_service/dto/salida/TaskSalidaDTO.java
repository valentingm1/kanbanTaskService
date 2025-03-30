package com.example.task_service.dto.salida;

import com.example.task_service.entity.Task;

import java.util.Set;

public class TaskSalidaDTO {

    private String title;
    private String nominalTime;
    private Set<Task> dependencies;
    private Set<Long> assigneeIds;
    private Long id;
    private Long swimlaneId;


    public TaskSalidaDTO(String title, String nominalTime, Set<Task> dependencies, Set<Long> assigneeIds, Long id, Long swimlaneId) {
        this.title = title;
        this.nominalTime = nominalTime;
        this.dependencies = dependencies;
        this.assigneeIds = assigneeIds;
        this.id = id;
    }

    public TaskSalidaDTO() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSwimlaneId() {
        return swimlaneId;
    }

    public void setSwimlaneId(Long swimlaneId) {
        this.swimlaneId = swimlaneId;
    }
}
