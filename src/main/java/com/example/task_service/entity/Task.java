package com.example.task_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;

import java.util.Set;

@Entity
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    @NotNull
    private String nominalTime;

    @ManyToMany
    @JoinTable(
            name = "task_dependencies",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "dependency_id")
    )
    private Set<Task> dependencies = new HashSet<>();

    // Guardamos los IDs de los usuarios en lugar de una relación con la entidad User
    @ElementCollection
    @CollectionTable(name = "task_assignees", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "user_id")
    private Set<Long> assigneeIds = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "swimlane_id", nullable = false)
    private Swimlane swimlane;


    public Task(Long id, String title, String nominalTime, Set<Task> dependencies, Set<Long> assigneeIds, Swimlane swimlane) {
        this.id = id;
        this.title = title;
        this.nominalTime = nominalTime;
        this.dependencies = dependencies;
        this.assigneeIds = assigneeIds;
        this.swimlane = swimlane;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Swimlane getSwimlane() {
        return swimlane;
    }

    public void setSwimlane(Swimlane swimlane) {
        this.swimlane = swimlane;
    }
}
