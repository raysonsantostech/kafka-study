package br.com.raysonsantos.producer.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers:localhost:9091}")
    private String bootstrapAddress;

    @Bean
    @Qualifier("producerFactoryDefault")
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put("security.protocol", "SASL_PLAINTEXT");
        configProps.put("sasl.mechanism", "SCRAM-SHA-512");
        configProps.put("sasl.jaas.config", String.format(
                "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
                "superuser", "secret"));

        //configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000); // Alterar para 30
        // segundos
        // Auto complete abaixo: producerProperties.put(ProducerConfig.LINGER_MS_CONFIG,
        // 10);
        // configProps.put(ProducerConfig.LINGER_MS_CONFIG, 10);

        // configProps.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
        // LoggingProducerInterceptor.class.getName());

        return new DefaultKafkaProducerFactory<>(configProps);
    }


    
    @Bean
    @Primary
    @Qualifier("producerFactoryRetries")
    public ProducerFactory<String, String> producerFactoryRetries() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put("security.protocol", "SASL_PLAINTEXT");
        configProps.put("sasl.mechanism", "SCRAM-SHA-512");
        configProps.put("sasl.jaas.config", String.format(
                "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
                "superuser", "secret"));

        configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 120000); // Alterar para 2MIN
        configProps.put("retries", 6); // Number of retry attempts.
        configProps.put("delivery.timeout.ms", 10000); // Total time for retries before failing.
        
        configProps.put("request.timeout.ms", 9000); 

        configProps.put("retry.backoff.ms", 500); // Initial wait time between retries.
        configProps.put("retry.backoff.max.ms", 3000); // Maximum wait time for exponential backoff retries.    
            


        //configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000); // Alterar para 30
        // segundos
        // Auto complete abaixo: producerProperties.put(ProducerConfig.LINGER_MS_CONFIG,
        // 10);
        // configProps.put(ProducerConfig.LINGER_MS_CONFIG, 10);

        // configProps.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
        // LoggingProducerInterceptor.class.getName());

        return new DefaultKafkaProducerFactory<>(configProps);
    }




    // @Bean
    public KafkaTemplate<String, String> kafkaTemplate2(ProducerFactory<String, String> producerFactory) {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory);

        kafkaTemplate.setProducerListener(new ProducerListener<>() {
            @Override
            public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
                System.out.printf("Mensagem enviada com sucesso para o tópico %s, partição %d%n",
                        recordMetadata.topic(), recordMetadata.partition());
            }

            @Override
            public void onError(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata,
                    Exception exception) {
                System.err.printf("Falha ao enviar mensagem para o tópico %s. Tentando novamente... Erro: %s%n",
                        producerRecord.topic(), exception.getMessage());
            }
        });

        return kafkaTemplate;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactoryRetries());
    }

}