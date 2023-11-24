# Descrição
Esta aplicação tem por objetivo prover uma forma de listar eventos do kafka de forma que possamos prover os tópicos de forma dinâmica, sem a necessidade de codar cada listener.

## Como utilizar

### Back-end
* Adicionar a seguinte opção na "vm options": -Dspring.profiles.active=local
* Rodar o comando: docker-compose up

### Front-end
Com o backend rodando, abra o arquivo [index.html](front-end%2Findex.html) no seu navegador de preferencia.
A página conectará com o websocket automaticamente ao ser carregada.

## Algumas tecnologias

* Spring Kafka - https://spring.io/projects/spring-kafka
* WebSockets - https://docs.spring.io/spring-framework/reference/web/websocket.html
* Docker - https://docs.docker.com/

## Todo

* ~~Adicionar título para os eventos listados com data e hora e algum identificador (back e front)~~
* Possibilitar autenticação do kafka com TLS
