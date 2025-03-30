package com.example.task_service.service;

import com.example.task_service.dto.entrada.SwimlaneEntradaDTO;
import com.example.task_service.dto.salida.SwimlaneSalidaDTO;

import java.util.List;

public interface iSwimlaneService {
    SwimlaneSalidaDTO addSwimlane(SwimlaneEntradaDTO SwimlaneEntradaDTO);

    List<SwimlaneSalidaDTO> getAllSwimlanes();

    SwimlaneSalidaDTO getOneSwimlane(Long id);

    void deleteSwimlane(Long id);
}
