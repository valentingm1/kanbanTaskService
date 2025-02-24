package com.example.task_service.mapper;

import com.example.task_service.dto.entrada.TaskEntradaDTO;
import com.example.task_service.dto.salida.TaskSalidaDTO;
import com.example.task_service.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task EntradaDTOToEntity(TaskEntradaDTO TaskEntradaDTO);

    TaskSalidaDTO EntityToSalidaDTO(Task Task);
}
