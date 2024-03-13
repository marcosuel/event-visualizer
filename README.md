# Descrição / Description
Pt-br
Esta aplicação tem por objetivo prover uma forma de listar eventos do kafka de forma que possamos prover os tópicos de forma dinâmica, sem a necessidade de codificar cada listener individualmente.

En
This application aims to provide a way to list Kafka events so that we can dynamically provide the topics without the need to code each listener individually.


## Como utilizar / How to use

### Back-end
Pt-br
* Adicionar a seguinte opção na "vm options": -Dspring.profiles.active=local
* Rodar o comando: docker-compose up


En
* Add the following option to "vm options": -Dspring.profiles.active=local
* Run the command: docker-compose up

### Front-end
Pt-br
Com o backend rodando, abra o arquivo [index.html](front-end%2Findex.html) no seu navegador de preferencia.
A página conectará com o websocket automaticamente ao ser carregada.

En
With the backend running, open the [index.html](front-end%2Findex.html) file in your preferred web browser. The page will automatically connect to the WebSocket upon loading.

## Algumas tecnologias / Some technologies

## To do list
Pt-br
* ~~Adicionar título para os eventos listados com data e hora e algum identificador (back e front)~~
* Possibilitar autenticação do kafka com TLS
  
En
* ~~Add a title for the events listed with date and time and some identifier (back and front)"~~

"Enable Kafka authentication with TLS"
