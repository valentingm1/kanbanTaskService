package com.example.task_service.mapper;

import com.example.task_service.dto.entrada.TaskEntradaDTO;
import com.example.task_service.dto.salida.TaskSalidaDTO;
import com.example.task_service.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task EntradaDTOToEntity(TaskEntradaDTO TaskEntradaDTO);

    @Mapping(source = "swimlane.id", target = "swimlaneId")
    TaskSalidaDTO EntityToSalidaDTO(Task Task);
}
