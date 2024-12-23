package br.com.raysonsantos.producer.config;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoggingProducerInterceptor implements ProducerInterceptor<String, String> {
    private static final Logger logger = LoggerFactory.getLogger(LoggingProducerInterceptor.class);

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        logger.info("[INTERCEPTOR onSend] Sending message: {}", record.value());
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception == null) {
            logger.info("[INTERCEPTOR onAcknowledgement] Message sent successfully to topic {} partition {} with offset {}", 
                        metadata.topic(), metadata.partition(), metadata.offset());
        } else {
            logger.error("[INTERCEPTOR onAcknowledgement] Error sending message", exception);
        }
    }

    @Override
    public void close() {
        // No-op
    }

    @Override
    public void configure(Map<String, ?> configs) {
        // No-op
    }
}