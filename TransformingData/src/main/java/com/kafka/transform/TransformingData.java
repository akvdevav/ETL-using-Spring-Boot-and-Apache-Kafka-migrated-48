package com.kafka.transform;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableRabbit
public class TransformingData {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queuesToDeclare = @Queue(name = "source_queue"))
    public void processMessage(String value) {
        int res = 0;
        String[] fileData = value.split(",");
        String operator = fileData[2];

        switch (operator) {
            case "+":
                res = Integer.parseInt(fileData[0]) + Integer.parseInt(fileData[1]);
                break;
            case "-":
                res = Integer.parseInt(fileData[0]) - Integer.parseInt(fileData[1]);
                break;
            case "*":
                res = Integer.parseInt(fileData[0]) * Integer.parseInt(fileData[1]);
                break;
            case "/":
                res = Integer.parseInt(fileData[0]) / Integer.parseInt(fileData[1]);
                break;
            default:
                // handle unknown operator if needed
                break;
        }
        String result = value + "," + res;
        rabbitTemplate.convertAndSend("target_queue", result);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TransformingData.class);
        Map<String, Object> defaultProps = new HashMap<>();
        defaultProps.put("server.port", "8084");
        app.setDefaultProperties(defaultProps);
        app.run(args);
    }

    // Declare source and target queues so they are auto‑created at startup
    @org.springframework.context.annotation.Bean
    public org.springframework.amqp.core.Queue sourceQueue() {
        return new org.springframework.amqp.core.Queue("source_queue", true);
    }

    @org.springframework.context.annotation.Bean
    public org.springframework.amqp.core.Queue targetQueue() {
        return new org.springframework.amqp.core.Queue("target_queue", true);
    }
}