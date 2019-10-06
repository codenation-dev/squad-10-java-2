# Squad 10 AceleraDev Java-2

## Passo a passo para executar o sistema

No diretório `log`:

`./mvnw clean package`

No diretório `agent`:

`./mvnw clean package`

No diretório `message-dto`

`mvn clean install`

No diretório do projeto (`squad-10-java-2`):

`docker-compose build`

`docker-compose up`


## Segurança

Fazer `POST` em `http://localhost:8070/oauth/token` 

Authorization: 'Basic Auth', username: squad10, password: squad10

Body: grant_type:password, username:squad, password:123

Retorno JWT. Usar acces_token para autorizar requisições.

No Swagger: clicar no botão authorize e preencher o campo com `bearer <access_token>`.