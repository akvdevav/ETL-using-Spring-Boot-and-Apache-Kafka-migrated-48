package com.example.demo.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for RabbitMQ resources previously named as Kafka topics.
 * Provides the admin, queues and a default {@link RabbitTemplate}.
 */
@Configuration
public class KafkaTopicConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    @ConditionalOnMissingBean(RabbitTemplate.class)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    /**
     * Queue that replaces the legacy {@code source_topic} Kafka topic.
     */
    @Bean
    public Queue sourceTopicQueue() {
        return new Queue("source_topic", true);
    }

    /**
     * Queue used by the TransformingData service as the source of messages.
     */
    @Bean
    public Queue sourceQueue() {
        return new Queue("source_queue", true);
    }

    /**
     * Queue used by the TransformingData service as the target for processed messages.
     */
    @Bean
    public Queue targetQueue() {
        return new Queue("target_queue", true);
    }
}