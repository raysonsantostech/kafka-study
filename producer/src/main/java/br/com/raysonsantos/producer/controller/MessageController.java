package br.com.raysonsantos.producer.controller;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.raysonsantos.producer.model.Message;
import br.com.raysonsantos.producer.service.ProducerService;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    // Configure o Logger nesse controller

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private ProducerService producerService;

    // POST Message
    @PostMapping("/publish")
    public void publishMessage(@RequestBody String message) {
        logger.info("[PUBLISH_MESSAGE] Publishing message... " + message + " - Size: " + calculateSizeInKB(message) + " KB");
        producerService.publishMessage(new Message(message));
    }

    // POST Message
    @PostMapping("/publish/random")
    public void publishMessagerRandom() {
        logger.info("[PUBLISH_MESSAGE_RANDOM] Publishing message... ");
        producerService.publishMessage();
    }

    @PostMapping("/publish/retryable")
    public void publishMessageRetryable(@RequestBody String message) {
        logger.info("[PUBLISH_MESSAGE_RETRYABLE] Publishing message... " + message + " - Size: " + calculateSizeInKB(message) + " KB");
        producerService.publishMessageRetryable(new Message(message));
    }

    @PostMapping("/publish/retrytemplate")
    public void publishMessageRetryTemplate(@RequestBody String message) {
        logger.info("[PUBLISH_MESSAGE_RETRY_TEMPLATE] Publishing message... " + message + " - Size: " + calculateSizeInKB(message) + " KB");
        producerService.publishMessageRetryTemplate(new Message(message));
    }
    
    private static double calculateSizeInKB(String text) {
        if (text == null) return 0.0;
        
        int sizeInBytes = text.getBytes(StandardCharsets.UTF_8).length;
        return sizeInBytes / 1024.0;
    }
}

