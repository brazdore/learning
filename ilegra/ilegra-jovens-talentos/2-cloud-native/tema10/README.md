
# tema10 -- Microservices/Hystrix

## Preparação

> Repositório		
- Clonar este repositório.

> JDK
- Instalar [JDK](https://jdk.java.net/) 8 ou maior.

> Docker
- Instalar e configurar o [Docker](https://docs.docker.com/engine/install/).

> Hystrix Dashboard
- Baixar o WAR do [Hystrix Dashboard](https://mvnrepository.com/artifact/com.netflix.hystrix/hystrix-dashboard/1.5.18).

> Tomcat
- Baixar o [Tomcat 9](https://tomcat.apache.org/download-90.cgi).

> Twitter
- Gerar as duas **`Consumer Keys [API Key and Secret]`** da [API do Twitter](https://developer.twitter.com/en/docs/apps/overview).
- Inserir os valores gerados em `tema10/twitter/src/main/resources/application.properties`
- `CONSUMER_KEY=${API Key}`
- `CONSUMER_SECRET=${API Key Secret}`

> GitHub
- Gerar um **`Personal Acess Token`** do [GitHub](https://docs.github.com/pt/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token).
- Inserir o valor gerado em `tema10/github/src/main/resources/application.properties`
- `GITHUB_TOKEN=${PERSONAL ACESS TOKEN}`

> **Exemplos**
- `CONSUMER_KEY=84zS2VxmvEyTtYYOqRs6jnSGP`
- `CONSUMER_SECRET=WBLIzqQp84ipu26ZsMgckatPzR6OowRfShD2lmTTxGorapWZ4P`
- `GITHUB_TOKEN=ghp_k4sPP2GZJ6jPBS2mb2ZxdckfaYfNvV9HPGJd`

## Subindo o Hystrix Dashboard com Tomcat 9
- Após baixar e instalar ou extrair o Tomcat 9, navegue até sua raíz e localize a pasta webapps
- Após baixar o arquivo WAR do Hystrix Dashboard, cole-o na pasta webapps do Tomcat 9
- A partir da pasta raíz do Tomcat 9, execute o comando `bin/catalina.sh start`

## Acessando o Hystrix Dashboard
- O Hystrix Dashboard deve estar disponível através do endpoint http://localhost:8080/{hystrix}
- Note que {hystrix} depende do nome do arquivo WAR. Se o nome do WAR é `hystrix-dashboard-1.5.18`, por exemplo, o endpoint será http://localhost:8080/hystrix-dashboard-1.5.18

## Configurando o Hystrix Dashboard
- Na página inicial, adicione os endpoints a serem observados:

-`http://localhost:8081/hystrix.stream`
-`http://localhost:8082/hystrix.stream`
-`http://localhost:8083/hystrix.stream`

- Basta colar estas URLs na caixa de texto e pressionar `Add Stream`. 
- Depois que as três estiverem adicionadas, clique em `Monitor Streams`.

## Gerando as Imagens & Executando os Containers

- A partir da pasta raíz do projeto, execute o comando `bash start.sh start`
- Sucesso: o comando `sudo docker container ls` deve listar três novos containers com as seguintes configurações:

-- `IMAGE: twitter-3702`  `PORT: 8081:8081`
-- `IMAGE: github-3702` `PORT: 8082:8082`
-- `IMAGE: sum-3702` `PORT: 8083:8083`

## Endpoints

> Twitter

- Os endpoints do *microservice* do Twitter estão em: `http://localhost:8081/twitter/`

 -- `http://localhost:8081/twitter/u/{username}`
 -- `http://localhost:8081/twitter/c/{username}`

> GitHub

- Os endpoints do *microservice* do GitHub estão em: `http://localhost:8082/github/`

 -- `http://localhost:8082/github/u/{username}`
 -- `http://localhost:8082/github/c/{username}`

> Sum

- Os endpoints do *microservice* de Sum estão em: `http://localhost:8083/sum/`

 -- `http://localhost:8083/sum/u/{username}`
 -- `http://localhost:8083/sum/c/{username}`
 
> **Exemplos**

- `http://localhost:8081/twitter/u/takanashikiara`
- `http://localhost:8081/twitter/c/takanashikiara`

- `http://localhost:8082/github/u/torvalds`
- `http://localhost:8082/github/c/torvalds`

- `http://localhost:8083/sum/u/a`
- `http://localhost:8083/sum/c/a`

## Encerrar a execução do Tomcat 9
- A partir da pasta raíz do Tomcat, execute o comando `bin/shutdown.sh`

## Parando o Docker

- A partir da pasta raíz do projeto, execute o comando `bash start.sh stop`
- Sucesso: o comando `sudo docker container ls` **NÃO** deve listar aqueles três containers criados:

-- `IMAGE: twitter-3702`  `PORT: 8081:8081`
-- `IMAGE: github-3702` `PORT: 8082:8082`
-- `IMAGE: sum-3702` `PORT: 8083:8083`

- Mesmo parados, os *containers* continuam a existir no sistema. Eles podem ser encontrados através do comando `sudo docker container ls -a` e podem ser apagados com `sudo docker container rm ${CONTAINER ID}`