package br.com.raysonsantos.producer.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import br.com.raysonsantos.producer.model.Message;

import org.apache.kafka.common.errors.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    private static final String TOPIC_HELLO = "hello-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMessage() {
        var message = new Message();
        kafkaTemplate.send(TOPIC_HELLO, message.getIdentifier(), message.getMessage());
        System.out.println("Publish message: " + message);
    }


    @Autowired
    private RetryTemplate retryTemplate;

//    @RetryableTopic(
//        backoff = @Backoff(value = 3000L),
//        attempts = "5",
//        autoCreateTopics = "false",
//        include = RuntimeException.class)

    @Retryable(
        value = { TimeoutException.class },
        maxAttempts = 5,
        backoff = @Backoff(delay = 1000)
    )
    public void publishMessage(Message message) {
        logger.info("Tentando enviar mensagem: {}", message);

        retryTemplate.execute(context -> {
            kafkaTemplate.send(TOPIC_HELLO, message.getIdentifier(), message.getMessage());
            return null;
        });


        //kafkaTemplate.send(TOPIC_HELLO, message.getIdentifier(), message.getMessage());
        System.out.println("Publish message: " + message);
    }
}