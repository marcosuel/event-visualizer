# Descrição
Configuração para enviar/receber tópicos kafka de forma genérica, sem precisa configurar individualmente cada tópico.

## Como utilizar

* Adicionar a seguinte opção na "vm options": -Dspring.profiles.active=local

* Rodar o comando: docker-compose up


## Algumas tecnologias

* Spring Kafka - https://spring.io/projects/spring-kafka
* Kafka Avro Serializer - https://mvnrepository.com/artifact/io.confluent/kafka-avro-serializer/5.5.0
