package com.example.task_service.service.impl;


import com.example.task_service.dto.entrada.ChangeSwimlaneEntradaDTO;
import com.example.task_service.dto.entrada.TaskEntradaDTO;
import com.example.task_service.dto.salida.ChangeSwimlaneSalidaDTO;
import com.example.task_service.dto.salida.TaskSalidaDTO;
import com.example.task_service.entity.Swimlane;
import com.example.task_service.entity.Task;
import com.example.task_service.events.UserValidationPublisher;
import com.example.task_service.mapper.TaskMapper;
import com.example.task_service.repository.SwimlaneRepository;
import com.example.task_service.repository.TaskRepository;
import com.example.task_service.service.iTaskService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService implements iTaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserValidationPublisher UserValidationPublisher;
    private final SwimlaneRepository swimlaneRepository;
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, UserValidationPublisher taskEventPublisher, SwimlaneRepository swimlaneRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.UserValidationPublisher = taskEventPublisher;
        this.swimlaneRepository = swimlaneRepository;
    }


    @Override
    public TaskSalidaDTO addTask(TaskEntradaDTO taskEntradaDTO) {
        log.info("📝 Iniciando la creación de una nueva tarea...");

        if (taskEntradaDTO.getTitle() == null || taskEntradaDTO.getTitle().trim().isEmpty()) {
            log.error("⚠ Error: El título de la tarea es obligatorio.");
            throw new IllegalArgumentException("El título de la tarea no puede estar vacío.");
        }

        if (taskEntradaDTO.getNominalTime() == null || taskEntradaDTO.getNominalTime().trim().isEmpty()) {
            log.warn("⚠ Error: El tiempo nominal de la tarea es obligatorio.");
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

        // Asignar siempre a la Swimlane con ID 1
        Swimlane defaultSwimlane = swimlaneRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("No existe una Swimlane con ID 1 en la base de datos"));

        mappedTask.setSwimlane(defaultSwimlane);
        log.info("✅ Tarea asignada a la Swimlane ID: 1");

        // Validar dependencias
        Set<Task> dependencies = new HashSet<>();
        if (taskEntradaDTO.getDependencyIds() != null && !taskEntradaDTO.getDependencyIds().isEmpty()) {
            List<Task> foundDependencies = taskRepository.findAllById(taskEntradaDTO.getDependencyIds());

            if (foundDependencies.size() != taskEntradaDTO.getDependencyIds().size()) {
                log.error("⚠ Error: No todas las dependencias existen en la base de datos. IDs enviados: {}",
                        taskEntradaDTO.getDependencyIds());
                throw new IllegalArgumentException("Algunas dependencias no existen en la base de datos.");
            }

            dependencies = new HashSet<>(foundDependencies);
        }

        mappedTask.setDependencies(dependencies);

        // Guardar en base de datos
        Task savedTask = taskRepository.save(mappedTask);
        log.info("✅ Tarea '{}' creada exitosamente con ID: {}", savedTask.getTitle(), savedTask.getId());

        // Validación extra: evitar auto-dependencia
        if (savedTask.getDependencies().contains(savedTask)) {
            log.error("⚠ Error: La tarea '{}' (ID: {}) no puede depender de sí misma.",
                    savedTask.getTitle(), savedTask.getId());
            throw new IllegalStateException("Error interno: La tarea se asignó como su propia dependencia.");
        }

        // Validar IDs de usuarios asignados
        if (taskEntradaDTO.getAssigneeIds() != null && !taskEntradaDTO.getAssigneeIds().isEmpty()) {
            log.info("🔍 Enviando solicitud de validación de usuarios para la tarea ID: {}", savedTask.getId());
            UserValidationPublisher.publishUserValidationRequest(savedTask.getId(), taskEntradaDTO.getAssigneeIds());
        }

        // Convertir la entidad guardada a DTO de salida
        TaskSalidaDTO salidaDTO = taskMapper.EntityToSalidaDTO(savedTask);
        salidaDTO.setAssigneeIds(savedTask.getAssigneeIds());

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

    public void updateTaskWithValidUsers(Long taskId, Set<Long> validUserIds) {
        log.info("🔄 Actualizando tarea ID: {} con usuarios validados: {}", taskId, validUserIds);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada con ID: " + taskId));

        task.setAssigneeIds(validUserIds);
        taskRepository.save(task);

        log.info("✅ Tarea ID: {} actualizada correctamente con {} usuarios validados.", taskId, validUserIds.size());
    }

    @Override
    public ChangeSwimlaneSalidaDTO moveTask(Long taskId, ChangeSwimlaneEntradaDTO request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        Swimlane newSwimlane = swimlaneRepository.findById(request.getSwimlaneId())
                .orElseThrow(() -> new EntityNotFoundException("Swimlane not found"));

        task.setSwimlane(newSwimlane);
        taskRepository.save(task);

        ChangeSwimlaneSalidaDTO changedSwimlane = new ChangeSwimlaneSalidaDTO(task.getId(), task.getSwimlane().getId());
        System.out.println(changedSwimlane.getSwimlaneId());
        System.out.println(changedSwimlane.getTaskId());

        return changedSwimlane;
    }

}
