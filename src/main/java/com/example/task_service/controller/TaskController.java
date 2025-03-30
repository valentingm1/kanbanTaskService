package com.example.task_service.controller;

import com.example.task_service.dto.entrada.ChangeSwimlaneEntradaDTO;
import com.example.task_service.dto.entrada.TaskEntradaDTO;
import com.example.task_service.dto.salida.ChangeSwimlaneSalidaDTO;
import com.example.task_service.dto.salida.TaskSalidaDTO;
import com.example.task_service.service.iTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/task")
@Tag(name = "Task", description = "All /task/ related endpoints")
public class TaskController {

    private final iTaskService TaskService;

    @Autowired
    public TaskController(iTaskService taskService) {
        this.TaskService = taskService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new task", description = "Creates a new task and returns the created entity.")
    public ResponseEntity<TaskSalidaDTO> addNewTask(@Validated @RequestBody TaskEntradaDTO taskEntradaDTO) {
        return new ResponseEntity<>(TaskService.addTask(taskEntradaDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    @Operation(summary = "Get all tasks", description = "Retrieves a list of all tasks.")
    public ResponseEntity<List<TaskSalidaDTO>> getAllTasks() {
        List<TaskSalidaDTO> tasks = TaskService.getAllTasks();

        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get a task by ID", description = "Retrieves a task given its ID.")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        try {
            TaskSalidaDTO task = TaskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{taskId}/move")
    @Operation(summary = "Move task to another swimlane", description = "Updates the swimlane ID of a task")
    public ResponseEntity<ChangeSwimlaneSalidaDTO> moveTask(
            @PathVariable Long taskId,
            @RequestBody @Validated ChangeSwimlaneEntradaDTO request) {
        return ResponseEntity.ok(TaskService.moveTask(taskId, request));
    }


}
