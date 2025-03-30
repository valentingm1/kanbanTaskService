package com.example.task_service.dto.entrada;

public class ChangeSwimlaneEntradaDTO {
    private Long swimlaneId;

    public ChangeSwimlaneEntradaDTO(Long swimlaneId) {
        this.swimlaneId = swimlaneId;
    }

    public Long getSwimlaneId() {
        return swimlaneId;
    }

    public void setSwimlaneId(Long swimlaneId) {
        this.swimlaneId = swimlaneId;
    }
}
