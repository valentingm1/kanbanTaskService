package com.example.task_service.mapper;

import com.example.task_service.dto.entrada.SwimlaneEntradaDTO;
import com.example.task_service.dto.salida.SwimlaneSalidaDTO;
import com.example.task_service.entity.Swimlane;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SwimlaneMapper {

    Swimlane EntradaDTOtoEntity(SwimlaneEntradaDTO swimlaneEntradaDTO);


    SwimlaneSalidaDTO EntityToSalidaDTO(Swimlane swimlane);
}
