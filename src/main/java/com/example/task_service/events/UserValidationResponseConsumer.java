package com.example.task_service.events;

import com.example.task_service.entity.Task;
import com.example.task_service.repository.TaskRepository;
import com.example.task_service.events.UserValidationResponseEvent;
import com.example.task_service.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserValidationResponseConsumer {

    private final TaskRepository taskRepository;

    public UserValidationResponseConsumer(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_RESPONSE)
    @Transactional
    public void receiveValidUsers(UserValidationResponseEvent event) {
        System.out.println("Usuarios válidos recibidos para la tarea ID: " + event.getTaskId() + " -> " + event.getValidUserIds());

        // ⚠ Verificar si la respuesta es nula o tiene un taskId inválido
        if (event.getTaskId() == null) {
            System.out.println("❌ Error: Se recibió una respuesta con taskId nulo.");
            return;  // Evita que se intente procesar un ID nulo
        }

        Optional<Task> taskOptional = taskRepository.findById(event.getTaskId());

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setAssigneeIds(event.getValidUserIds());

            taskRepository.save(task);
            System.out.println("✅ Tarea actualizada con usuarios válidos.");
        } else {
            System.out.println("⚠ Error: No se encontró la tarea con ID " + event.getTaskId());
        }
    }
}
