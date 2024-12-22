# kafka-study
Kafka Study

## Kafka Docker Compose

- Qual a diferença entre?
> docker.redpanda.com/redpandadata/console:latest
> docker.redpanda.com/vectorized/console:latest

## Kafka CLI

### Download Binary
> wget https://dlcdn.apache.org/kafka/3.9.0/kafka_2.12-3.9.0.tgz && tar -xzf kafka_2.12-3.9.0.tgz && rm -rf kafka-cli && mv kafka_2.12-3.9.0 kafka-cli && rm kafka_2.12-3.9.0.tgz

### Configuração do Ambiente CLI

Para configurar o Kafka no GitHub Codespaces e usar os scripts de qualquer caminho, siga os passos abaixo:

1. Verifique qual shell você está usando:

```ssh
echo $SHELL
```

2. Dependendo do shell, adicione o caminho do Kafka ao arquivo de configuração apropriado (.bashrc para bash ou .zshrc para zsh):

```bash
# Para bash, adicione ao ~/.bashrc
echo 'export PATH=$PATH:/workspaces/kafka-study/kafka-cli/bin' >> ~/.bashrc
source ~/.bashrc

# Para zsh, adicione ao ~/.zshrc
echo 'export PATH=$PATH:/workspaces/kafka-study/kafka-cli/bin' >> ~/.zshrc
source ~/.zshrc
```
3. Agora você pode usar os scripts Kafka de qualquer caminho, por exemplo:

```bash
kafka-topics.sh --list --bootstrap-server localhost:9092
```


##

##

##






