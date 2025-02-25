package com.example.task_service.controller;

import com.example.task_service.dto.entrada.TaskEntradaDTO;
import com.example.task_service.dto.salida.TaskSalidaDTO;
import com.example.task_service.service.iTaskService;
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
public class TaskController {

    private iTaskService TaskService;

    @Autowired
    public TaskController(iTaskService taskService) {
        TaskService = taskService;
    }

    @PostMapping("/add")
    public ResponseEntity<TaskSalidaDTO> addNewTask(@Validated @RequestBody TaskEntradaDTO taskEntradaDTO){
        return new ResponseEntity<>(TaskService.addTask(taskEntradaDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<TaskSalidaDTO>> getAllTasks() {
        List<TaskSalidaDTO> tasks = TaskService.getAllTasks();

        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        try {
            TaskSalidaDTO task = TaskService.getTaskById(id);
            return ResponseEntity.ok(task); // 200 OK si la tarea existe
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request si el ID es inválido
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found si no se encuentra
        }
    }


}
