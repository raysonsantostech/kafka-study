package br.com.raysonsantos.producer.service;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import br.com.raysonsantos.producer.model.Message;

@Service
public class ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    private static final String TOPIC_HELLO = "hello-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RetryTemplate retryTemplate;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate, RetryTemplate retryTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.retryTemplate = retryTemplate;
    }

    public void publishMessage() {
        var message = new Message();

        logger.info("[PUBLISH MESSAGE RANDOM] Mensage: {}", message);
        try {
            kafkaTemplate.send(TOPIC_HELLO, message.getIdentifier(), message.getMessage()).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("MARIAMARIAMARIAMARIAMARIAMARIAMARIA");
        }
    }

    public void publishMessage(Message message) {
        logger.info("[PUBLISH MESSAGE @param:message] Mensage: {}", message);
        kafkaTemplate.send(TOPIC_HELLO, message.getIdentifier(), message.getMessage());
    }

    @Retryable
    public void publishMessageRetryable(Message message) {
        logger.info("[PUBLISH MESSAGE @RETRYABLE] Mensage: {}", message);
        kafkaTemplate.send(TOPIC_HELLO, message.getIdentifier(), message.getMessage());
    }

    public void publishMessageRetryTemplate(Message message) {
        logger.info("[PUBLISH MESSAGE RETRY TEMPLATE] Mensage: {}", message);

        retryTemplate.execute(context -> {
            kafkaTemplate.send(TOPIC_HELLO, message.getIdentifier(), message.getMessage());
            return null;
        });
    }
}