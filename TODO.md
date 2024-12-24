# Producer Spring

# Consumer Spring

# ANONYMOUS?

docker run -p 8080:8080 
-e KAFKA_BROKERS=pkc-4r000.europe-west1.gcp.confluent.cloud:9092 
-e KAFKA_TLS_ENABLED=true
-e KAFKA_SASL_ENABLED=true 
-e KAFKA_SASL_USERNAME=xxx 
-e KAFKA_SASL_PASSWORD=xxx 

docker.redpanda.com/redpandadata/console:latest

org.apache.kafka.common.config.ConfigException: 

delivery.timeout.ms should be equal to or larger than linger.ms + request.timeout.ms
  