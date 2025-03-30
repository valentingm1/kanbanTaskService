package com.example.task_service.dto.salida;

public class ChangeSwimlaneSalidaDTO {
    private Long taskId;
    private Long swimlaneId;

    public ChangeSwimlaneSalidaDTO(Long taskId, Long swimlaneId) {
        this.taskId = taskId;
        this.swimlaneId = swimlaneId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getSwimlaneId() {
        return swimlaneId;
    }

    public void setSwimlaneId(Long swimlaneId) {
        this.swimlaneId = swimlaneId;
    }
}
