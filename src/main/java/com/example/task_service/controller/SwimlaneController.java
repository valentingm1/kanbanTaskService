package com.example.task_service.controller;


import com.example.task_service.dto.entrada.SwimlaneEntradaDTO;
import com.example.task_service.dto.salida.SwimlaneSalidaDTO;
import com.example.task_service.service.iSwimlaneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/swimlane")
@Tag(name = "Swimlane", description = "All /swimlane/ related endpoints")
public class SwimlaneController {
    private final iSwimlaneService SwimlaneService;

    @Autowired
    public SwimlaneController(iSwimlaneService swimlaneService) {
        this.SwimlaneService = swimlaneService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new swimlane", description = "Creates a new swimlane and returns the created entity.")
    public ResponseEntity<SwimlaneSalidaDTO> addNewSwimlane(@Valid @RequestBody SwimlaneEntradaDTO swimlaneEntradaDTO) {
        return new ResponseEntity<>(SwimlaneService.addSwimlane(swimlaneEntradaDTO), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all swimlanes", description = "Retrieves a list of all swimlanes.")
    public ResponseEntity<List<SwimlaneSalidaDTO>> getAllSwimlanes() {
        return ResponseEntity.ok(SwimlaneService.getAllSwimlanes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a swimlane by ID", description = "Retrieves a swimlane given its ID.")
    public ResponseEntity<SwimlaneSalidaDTO> getOneSwimlane(@PathVariable Long id) {
        return ResponseEntity.ok(SwimlaneService.getOneSwimlane(id));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a swimlane", description = "Deletes a swimlane by ID and returns a confirmation message.")
    public ResponseEntity<String> deleteSwimlane(@PathVariable Long id) {
        try {
            SwimlaneService.deleteSwimlane(id);
            return ResponseEntity.ok("Swimlane eliminada correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
