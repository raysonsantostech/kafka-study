```bash
# Com o usuário: Superuser
kafka-topics.sh --list --bootstrap-server localhost:29091 --command-config client.superuser.properties 
# hello-topic
# hi-topic

# Com o usuário: Alice
kafka-topics.sh --list --bootstrap-server localhost:29091 --command-config client.alice.properties 
# hello-topic

# Com o usuário: Bento
kafka-topics.sh --list --bootstrap-server localhost:29091 --command-config client.bento.properties 
# hi-topic

# Com o usuário: Joana
kafka-topics.sh --list --bootstrap-server localhost:29091 --command-config client.joana.properties 
# hello-topic
# hi-topic


# HELLO-TOPIC

## Publicar mensagens no `hello-topic`
echo "Hello, I'm SUPERUSER" | kafka-console-producer.sh --broker-list localhost:29091 --topic hello-topic --producer.config client.superuser.properties
echo "Hello, I'm ALICE" | kafka-console-producer.sh --broker-list localhost:29091 --topic hello-topic --producer.config client.alice.properties
echo "Hello, I'm JOANA" | kafka-console-producer.sh --broker-list localhost:29091 --topic hello-topic --producer.config client.joana.properties
# HI, I'm SUPERUSER
# HI, I'm BENTO
# HI, I'm JOANA
# ^CProcessed a total of 3 messages

echo "Hello, I'm BENTO" | kafka-console-producer.sh --broker-list localhost:29091 --topic hello-topic --producer.config client.bento.properties
# [2024-12-21 19:59:56,399] WARN [Producer clientId=console-producer] The metadata response from the cluster reported a recoverable issue with correlation id 2 : {hello-topic=TOPIC_AUTHORIZATION_FAILED} (org.apache.kafka.clients.NetworkClient)
# [2024-12-21 19:59:56,400] ERROR [Producer clientId=console-producer] Topic authorization failed for topics [hello-topic] (org.apache.kafka.clients.Metadata)
# [2024-12-21 19:59:56,403] ERROR Error when sending message to topic hello-topic with key: null, value: 16 bytes with error: (org.apache.kafka.clients.producer.internals.ErrorLoggingCallback)
# org.apache.kafka.common.errors.TopicAuthorizationException: Not authorized to access topics: [hello-topic]

## Consumir mensagens do `hello-topic`

kafka-console-consumer.sh --bootstrap-server localhost:29091 --topic hello-topic --from-beginning --consumer.config client.superuser.properties
kafka-console-consumer.sh --bootstrap-server localhost:29091 --topic hello-topic --from-beginning --consumer.config client.alice.properties
kafka-console-consumer.sh --bootstrap-server localhost:29091 --topic hello-topic --from-beginning --consumer.config client.joana.properties
# Hello, I'm SUPERUSER
# Hello, I'm ALICE
# Hello, I'm JOANA
# ^CProcessed a total of 3 messages

kafka-console-consumer.sh --bootstrap-server localhost:29091 --topic hello-topic --from-beginning --consumer.config client.bento.properties
# [2024-12-21 20:11:57,225] WARN [Consumer clientId=console-consumer, groupId=console-consumer-66994] The metadata response from the cluster reported a recoverable issue with correlation id 3 : {hello-topic=TOPIC_AUTHORIZATION_FAILED} (org.apache.kafka.clients.NetworkClient)
# [2024-12-21 20:11:57,226] ERROR [Consumer clientId=console-consumer, groupId=console-consumer-66994] Topic authorization failed for topics [hello-topic] (org.apache.kafka.clients.Metadata)
# [2024-12-21 20:11:57,230] ERROR Error processing message, terminating consumer process:  (org.apache.kafka.tools.consumer.ConsoleConsumer)
# org.apache.kafka.common.errors.TopicAuthorizationException: Not authorized to access topics: [hello-topic]
# Processed a total of 0 messages

# HI-TOPIC

## Publicar mensagens
echo "HI, I'm SUPERUSER" | kafka-console-producer.sh --broker-list localhost:29091 --topic hi-topic --producer.config client.superuser.properties

echo "HI, I'm BENTO" | kafka-console-producer.sh --broker-list localhost:29091 --topic hi-topic --producer.config client.bento.properties

echo "HI, I'm JOANA" | kafka-console-producer.sh --broker-list localhost:29091 --topic hi-topic --producer.config client.joana.properties

echo "HI, I'm ALICE" | kafka-console-producer.sh --broker-list localhost:29091 --topic hi-topic --producer.config client.alice.properties
# [2024-12-21 20:15:47,591] WARN [Producer clientId=console-producer] The metadata response from the cluster reported a recoverable issue with correlation id 2 : {hi-topic=TOPIC_AUTHORIZATION_FAILED} (org.apache.kafka.clients.NetworkClient)
# [2024-12-21 20:15:47,607] ERROR [Producer clientId=console-producer] Topic authorization failed for topics [hi-topic] (org.apache.kafka.clients.Metadata)
# [2024-12-21 20:15:47,609] ERROR Error when sending message to topic hi-topic with key: null, value: 13 bytes with error: (org.apache.kafka.clients.producer.internals.ErrorLoggingCallback)
# org.apache.kafka.common.errors.TopicAuthorizationException: Not authorized to access topics: [hi-topic]

# Consumir mensagens
kafka-console-consumer.sh --bootstrap-server localhost:29091 --topic hi-topic --from-beginning --consumer.config client.superuser.properties

kafka-console-consumer.sh --bootstrap-server localhost:29091 --topic hi-topic --from-beginning --consumer.config client.bento.properties

kafka-console-consumer.sh --bootstrap-server localhost:29091 --topic hi-topic --from-beginning --consumer.config client.joana.properties

kafka-console-consumer.sh --bootstrap-server localhost:29091 --topic hi-topic --from-beginning --consumer.config client.alice.properties
# [2024-12-21 20:17:35,301] WARN [Consumer clientId=console-consumer, groupId=console-consumer-32117] The metadata response from the cluster reported a recoverable issue with correlation id 3 : {hi-topic=TOPIC_AUTHORIZATION_FAILED} (org.apache.kafka.clients.NetworkClient)
# [2024-12-21 20:17:35,303] ERROR [Consumer clientId=console-consumer, groupId=console-consumer-32117] Topic authorization failed for topics [hi-topic] (org.apache.kafka.clients.Metadata)
# [2024-12-21 20:17:35,304] ERROR Error processing message, terminating consumer process:  (org.apache.kafka.tools.consumer.ConsoleConsumer)
# org.apache.kafka.common.errors.TopicAuthorizationException: Not authorized to access topics: [hi-topic]
# Processed a total of 0 messages


echo "That's all, Folks!!"

```