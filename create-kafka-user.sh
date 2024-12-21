#!/bin/bash

echo "Hello World"

# Cria o tópico hello-topic
kafka-topics.sh --bootstrap-server kafka-broker-1:19091 --create \
    --topic hello-topic --partitions 1 --replication-factor 1

# Cria o tópico hi-topic
kafka-topics.sh --bootstrap-server kafka-broker-1:19091 --create \
    --topic hi-topic --partitions 1 --replication-factor 1

# Cria o usuário: superuser
kafka-configs.sh --bootstrap-server kafka-broker-1:19091 --alter \
    --add-config 'SCRAM-SHA-512=[iterations=8192,password=secret]' \
    --entity-type users --entity-name superuser

# ALICE
## Cria o usuário: alice
kafka-configs.sh --bootstrap-server kafka-broker-1:19091 --alter \
    --add-config 'SCRAM-SHA-512=[iterations=8192,password=secret]' \
    --entity-type users --entity-name alice

## Adiciona ACLs para o tópico hello-topic
kafka-acls.sh --bootstrap-server kafka-broker-1:19091 --add \
    --allow-principal User:alice \
    --operation All \
    --topic hello-topic

# BENTO  
## Cria o usuário: bento
kafka-configs.sh --bootstrap-server kafka-broker-1:19091 --alter \
    --add-config 'SCRAM-SHA-512=[iterations=8192,password=secret]' \
    --entity-type users --entity-name bento

## Adiciona ACLs para o tópico hi-topic [ --group '*' \ ]
kafka-acls.sh --bootstrap-server kafka-broker-1:19091 --add \
    --allow-principal User:bento \
    --operation All \
    --topic hi-topic

# JOANA
## Cria o usuário: joana
kafka-configs.sh --bootstrap-server kafka-broker-1:19091 --alter \
    --add-config 'SCRAM-SHA-512=[iterations=8192,password=secret]' \
    --entity-type users --entity-name joana

## Adiciona ACLs para o tópico hello-topic
kafka-acls.sh --bootstrap-server kafka-broker-1:19091 --add \
    --allow-principal User:joana \
    --operation All \
    --topic hello-topic

## Adiciona ACLs para o tópico hi-topic [ --group '*' \ ]
kafka-acls.sh --bootstrap-server kafka-broker-1:19091 --add \
    --allow-principal User:joana \
    --operation All \
    --topic hi-topic


# Lista usuários (describe)
kafka-configs.sh --bootstrap-server kafka-broker-1:19091 --describe \
  --entity-type users

# Lista ACLs para o tópico hello-topic
kafka-acls.sh --bootstrap-server kafka-broker-1:19091 --list \
    --topic hello-topic

# Lista ACLs para o tópico hi-topic
kafka-acls.sh --bootstrap-server kafka-broker-1:19091 --list \
    --topic hi-topic

echo "That's all, Folks!!"