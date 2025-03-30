package com.example.task_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Swimlane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la Swimlane no puede estar vacío")
    @NotNull
    private String name;


    @OneToMany(mappedBy = "swimlane", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> taskList = new ArrayList<>();

    public Swimlane(Long id, String name, List<Task> taskList) {
        this.id = id;
        this.name = name;
        this.taskList = taskList;
    }

    public Swimlane() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
