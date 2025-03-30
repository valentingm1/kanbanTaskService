package com.example.task_service.events;

import com.example.task_service.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;

@Service
public class UserValidationPublisher {

    private final RabbitTemplate rabbitTemplate;

    public UserValidationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserValidationRequest(UserValidationRequestEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_REQUEST, event);
    }

    public void publishUserValidationRequest(Long taskId, Set<Long> userIds) {
        UserValidationRequestEvent event = new UserValidationRequestEvent(taskId, userIds);
        System.out.println("EJECUTANDO PUBLISHUSERVALIDATION REQUEST 🔍🔍🔍");
        sendUserValidationRequest(event);
    }
}