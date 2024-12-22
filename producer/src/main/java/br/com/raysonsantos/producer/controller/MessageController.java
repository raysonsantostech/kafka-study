package br.com.raysonsantos.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.raysonsantos.producer.service.ProducerService;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("/publish")
    public void publishMessage() {
        System.out.println("Publishing message from GET...");
        producerService.publishMessage();
    }
}

