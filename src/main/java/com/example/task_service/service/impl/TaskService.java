package com.example.task_service.service.impl;


import com.example.task_service.dto.entrada.TaskEntradaDTO;
import com.example.task_service.dto.salida.TaskSalidaDTO;
import com.example.task_service.entity.Task;
import com.example.task_service.mapper.TaskMapper;
import com.example.task_service.repository.TaskRepository;
import com.example.task_service.service.iTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService implements iTaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    @Override
    public TaskSalidaDTO addTask(TaskEntradaDTO taskEntradaDTO) {
        log.info("📝 Iniciando la creación de una nueva tarea...");

        // Validación: El título no puede ser nulo o vacío
        if (taskEntradaDTO.getTitle() == null || taskEntradaDTO.getTitle().trim().isEmpty()) {
            log.warn("⚠ Error: El título de la tarea es obligatorio.");
            throw new IllegalArgumentException("El título de la tarea no puede estar vacío.");
        }

        // Validación: El tiempo nominal no puede ser nulo o vacío
        if (taskEntradaDTO.getNominalTime() == null || taskEntradaDTO.getNominalTime().trim().isEmpty()) {
            log.warn("Error: El tiempo nominal de la tarea es obligatorio.");
            throw new IllegalArgumentException("El tiempo nominal de la tarea no puede estar vacío.");
        }

        log.debug(" Datos recibidos -> Título: '{}', Tiempo nominal: '{}', Dependencias: {}, Asignados: {}",
                taskEntradaDTO.getTitle(),
                taskEntradaDTO.getNominalTime(),
                taskEntradaDTO.getDependencyIds() != null ? taskEntradaDTO.getDependencyIds().size() : 0,
                taskEntradaDTO.getAssigneeIds() != null ? taskEntradaDTO.getAssigneeIds().size() : 0
        );

        // Mapeo de DTO a entidad
        Task mappedTask = taskMapper.EntradaDTOToEntity(taskEntradaDTO);

        // Obtener y validar dependencias desde la base de datos
        Set<Task> dependencies = new HashSet<>();
        if (taskEntradaDTO.getDependencyIds() != null && !taskEntradaDTO.getDependencyIds().isEmpty()) {
            List<Task> foundDependencies = taskRepository.findAllById(taskEntradaDTO.getDependencyIds());

            // Validar que todas las dependencias existen
            if (foundDependencies.size() != taskEntradaDTO.getDependencyIds().size()) {
                log.error(" Error: No todas las dependencias existen en la base de datos. IDs enviados: {}",
                        taskEntradaDTO.getDependencyIds());
                throw new IllegalArgumentException("Algunas dependencias no existen en la base de datos.");
            }

            dependencies = new HashSet<>(foundDependencies);
        }

        // Validación: Una tarea no puede depender de sí misma
        if (dependencies.contains(mappedTask)) {
            log.error("Error: La tarea '{}' (ID desconocido aún) no puede depender de sí misma.", mappedTask.getTitle());
            throw new IllegalArgumentException("Una tarea no puede ser su propia dependencia.");
        }

        // Validar IDs de usuarios asignados
        Set<Long> assigneeIds = new HashSet<>();
        if (taskEntradaDTO.getAssigneeIds() != null) {
            assigneeIds.addAll(taskEntradaDTO.getAssigneeIds().stream()
                    .filter(Objects::nonNull) // Evita nulos
                    .collect(Collectors.toSet()));
        }

        mappedTask.setDependencies(dependencies);
        mappedTask.setAssigneeIds(assigneeIds);

        // Guardar en base de datos
        Task savedTask = taskRepository.save(mappedTask);
        log.info("✅ Tarea '{}' creada exitosamente con ID: {}", savedTask.getTitle(), savedTask.getId());

        // Validación extra: Evitar auto-dependencia después de persistir (por seguridad)
        if (savedTask.getDependencies().contains(savedTask)) {
            log.error(" Error: La tarea '{}' (ID: {}) no puede depender de sí misma (detectado después de persistencia).",
                    savedTask.getTitle(), savedTask.getId());
            throw new IllegalStateException("Error interno: La tarea se asignó como su propia dependencia.");
        }

        // Convertir la entidad guardada a DTO de salida
        TaskSalidaDTO salidaDTO = taskMapper.EntityToSalidaDTO(savedTask);

        log.debug("📤 Respuesta enviada -> {}", salidaDTO);
        return salidaDTO;
    }

    @Override
    public TaskSalidaDTO getTaskById(Long id) {
        log.info("Looking for task with id {}", id);

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a poistive number");
        }

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task with id " + id +" not found"));

        return taskMapper.EntityToSalidaDTO(task);
    }

    @Override
    public List<TaskSalidaDTO> getAllTasks() {
        log.info("Fetching all tasks...");
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::EntityToSalidaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTaskById(Long id){
        log.info("Attempting to delete task with id {}", id);

        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task with id " + id + "not found"));

        taskRepository.delete(task);
    }




}
