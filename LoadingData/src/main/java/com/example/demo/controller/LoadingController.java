package com.example.demo.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.example.demo.service.LoadingService;

@Controller
public class LoadingController {

    private final LoadingService service;

    @Autowired
    public LoadingController(LoadingService service) {
        this.service = service;
    }

    @RabbitListener(queues = "target_topic")
    public void listen(String message) {
        System.out.println(message);
        service.loadDataToDB(message);
    }
}