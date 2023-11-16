# Descrição
Esta aplicação tem por objetivo prover uma forma de listar eventos do kafka de forma que possamos prover os tópicos de forma dinâmica, sem a necessidade de codar cada listener.

## Como utilizar

* Adicionar a seguinte opção na "vm options": -Dspring.profiles.active=local

* Rodar o comando: docker-compose up


## Algumas tecnologias

* Spring Kafka - https://spring.io/projects/spring-kafka
* Kafka Avro Serializer - https://mvnrepository.com/artifact/io.confluent/kafka-avro-serializer/5.5.0
