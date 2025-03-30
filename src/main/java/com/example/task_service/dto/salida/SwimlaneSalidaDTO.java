package com.example.task_service.dto.salida;

import com.example.task_service.entity.Task;

import java.util.List;

public class SwimlaneSalidaDTO {
    private Long id;
    private String name;
    private List<TaskSalidaDTO> taskList;

    public SwimlaneSalidaDTO(Long id, String name, List<TaskSalidaDTO> taskList) {
        this.id = id;
        this.name = name;
        this.taskList = taskList;
    }

    public SwimlaneSalidaDTO() {
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

    public List<TaskSalidaDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskSalidaDTO> taskList) {
        this.taskList = taskList;
    }
}
