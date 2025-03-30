package com.example.task_service.service;

import com.example.task_service.dto.entrada.ChangeSwimlaneEntradaDTO;
import com.example.task_service.dto.entrada.TaskEntradaDTO;
import com.example.task_service.dto.salida.ChangeSwimlaneSalidaDTO;
import com.example.task_service.dto.salida.TaskSalidaDTO;

import java.util.List;

public interface iTaskService {
    TaskSalidaDTO addTask(TaskEntradaDTO taskEntradaDTO);

    TaskSalidaDTO getTaskById(Long id);

    List<TaskSalidaDTO> getAllTasks();

    void deleteTaskById(Long id);

    ChangeSwimlaneSalidaDTO moveTask(Long taskId, ChangeSwimlaneEntradaDTO request);
}
