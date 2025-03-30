package com.example.task_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "user-validation-exchange";
    public static final String ROUTING_KEY_REQUEST = "request_routing_key";  // Envía la solicitud
    public static final String ROUTING_KEY_RESPONSE = "response_routing_key"; // Recibe la respuesta
    public static final String QUEUE_RESPONSE="response_queue";


    @Bean
    public Queue queueResponse(){
        return new Queue(QUEUE_RESPONSE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public Binding bindingResponse(Queue queueResponse,TopicExchange topicExchange){
        return BindingBuilder.bind(queueResponse).to(topicExchange).with(ROUTING_KEY_RESPONSE);
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final var template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}