package com.example.task_service.dto.entrada;

import jakarta.validation.constraints.NotBlank;

public class SwimlaneEntradaDTO {
    private String name;

    public SwimlaneEntradaDTO(String name) {
        this.name = name;
    }

    public SwimlaneEntradaDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
