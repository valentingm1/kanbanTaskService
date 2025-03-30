package com.example.task_service.service.impl;

import com.example.task_service.dto.entrada.SwimlaneEntradaDTO;
import com.example.task_service.dto.salida.SwimlaneSalidaDTO;
import com.example.task_service.entity.Swimlane;
import com.example.task_service.exceptions.SwimlaneAlreadyExistsException;
import com.example.task_service.mapper.SwimlaneMapper;
import com.example.task_service.repository.SwimlaneRepository;
import com.example.task_service.service.iSwimlaneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class SwimlaneService implements iSwimlaneService {
    private final SwimlaneRepository swimlaneRepository;
    private final SwimlaneMapper swimlaneMapper;
    private static final Logger log = LoggerFactory.getLogger(SwimlaneService.class);

    public SwimlaneService(SwimlaneRepository swimlaneRepository, SwimlaneMapper swimlaneMapper) {
        this.swimlaneRepository = swimlaneRepository;
        this.swimlaneMapper = swimlaneMapper;
    }

    @Override
    public SwimlaneSalidaDTO addSwimlane(SwimlaneEntradaDTO SwimlaneEntradaDTO) {
        log.info("📝 Adding Swimlane 🤽‍♂️🤽‍♂️🤽‍♂️🤽‍♂️");
        log.info("Mapping Swimlane 🌍");
        if (swimlaneRepository.existsByName(SwimlaneEntradaDTO.getName())) {
            log.warn("❌ No se puede crear la Swimlane: '{}' ya existe.", SwimlaneEntradaDTO.getName());
            throw new SwimlaneAlreadyExistsException("Ya existe una Swimlane con el nombre: " + SwimlaneEntradaDTO.getName());
        }


        Swimlane swimlaneToSave = swimlaneMapper.EntradaDTOtoEntity(SwimlaneEntradaDTO);

        if (swimlaneToSave.getTaskList() == null) {
            swimlaneToSave.setTaskList(new ArrayList<>());
        }


        log.info("Saving Swimlane... 💾");
        Swimlane savedSwimlane = swimlaneRepository.save(swimlaneToSave);
        System.out.println(savedSwimlane.getName());

        log.info("Mapping Swimlane to SalidaDTO");
        SwimlaneSalidaDTO swimlaneToReturn = swimlaneMapper.EntityToSalidaDTO(savedSwimlane);

        System.out.println(swimlaneToReturn.getName());

        log.info("Returning Swimlane");
        return swimlaneToReturn;
    }

    @Override
    public List<SwimlaneSalidaDTO> getAllSwimlanes() {
        log.info("Fetching all swimlanes from database...");

        List<Swimlane> swimlanes = swimlaneRepository.findAll();

        log.info("🔄 Mapping entities to DTOs...");
        List<SwimlaneSalidaDTO> swimlaneDTOs = swimlanes.stream()
                .map(swimlaneMapper::EntityToSalidaDTO)
                .collect(Collectors.toList());

        log.info("✅ Returning {} swimlanes", swimlaneDTOs.size());
        return swimlaneDTOs;
    }

    @Override
    public SwimlaneSalidaDTO getOneSwimlane(Long id){
        log.info("Fetching swimlane from database");
        Swimlane fetchedSwimlane = swimlaneRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Swimlane with id " + id +" not found"));
        return swimlaneMapper.EntityToSalidaDTO(fetchedSwimlane);

    }


    @Override
    public void deleteSwimlane(Long id) {
        if (id == 1) {
            throw new RuntimeException("🚫 No se puede eliminar la Swimlane con ID 1.");
        }
        swimlaneRepository.deleteById(id);
    }
}
