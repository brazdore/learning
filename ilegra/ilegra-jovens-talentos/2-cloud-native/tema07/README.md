
# tema07 -- Microservices

## Preparação

> Repositório		
- Clonar este repositório.

> Docker
- Instalar e configurar o [Docker](https://docs.docker.com/engine/install/).

> Twitter
- Gerar as duas **`Consumer Keys [API Key and Secret]`** da [API do Twitter](https://developer.twitter.com/en/docs/apps/overview).
- Inserir os valores gerados em `tema07/twitter/src/main/resources/application.properties`
- `CONSUMER_KEY=${API Key}`
- `CONSUMER_SECRET=${API Key Secret}`

> GitHub
- Gerar um **`Personal Acess Token`** do [GitHub](https://docs.github.com/pt/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token).
- Inserir o valor gerado em `tema07/github/src/main/resources/application.properties`
- `GITHUB_TOKEN=${PERSONAL ACESS TOKEN}`

> **Exemplos**
- `CONSUMER_KEY=84zS2VxmvEyTtYYOqRs6jnSGP`
- `CONSUMER_SECRET=WBLIzqQp84ipu26ZsMgckatPzR6OowRfShD2lmTTxGorapWZ4P`
- `GITHUB_TOKEN=ghp_k4sPP2GZJ6jPBS2mb2ZxdckfaYfNvV9HPGJd`


## Gerando as Imagens & Executando os Containers

- A partir da pasta raíz do projeto, execute o comando `bash start.sh start`
- Sucesso: o comando `sudo docker container ls` deve listar três novos containers com as seguintes configurações:

-- `IMAGE: twitter-3702`  `PORT: 8080:8080`
-- `IMAGE: github-3702` `PORT: 8081:8081`
-- `IMAGE: sum-3702` `PORT: 8082:8082`

## Endpoints

> Twitter

- Os endpoints do *microservice* do Twitter estão em: `http://localhost:8080/twitter/`

 -- `http://localhost:8080/twitter/user/{username}`
 -- `http://localhost:8080/twitter/count/{username}`

> GitHub

- Os endpoints do *microservice* do GitHub estão em: `http://localhost:8081/github/`

 -- `http://localhost:8081/github/user/{username}`
 -- `http://localhost:8081/github/count/{username}`

> Sum

- Os endpoints do *microservice* de Sum estão em: `http://localhost:8082/sum/`

 -- `http://localhost:8082/sum/summary/{username}`
 -- `http://localhost:8082/sum/count/{username}`
 
> **Exemplos**

- `http://localhost:8080/twitter/user/takanashikiara`
- `http://localhost:8080/twitter/count/takanashikiara`

- `http://localhost:8081/github/user/torvalds`
- `http://localhost:8081/github/count/torvalds`

- `http://localhost:8082/sum/summary/a`
- `http://localhost:8082/sum/count/a`

## Parando o Container

- A partir da pasta raíz do projeto, execute o comando `bash start.sh stop`
- Sucesso: o comando `sudo docker container ls` **NÃO** deve listar aqueles três containers criados:

-- `IMAGE: twitter-3702`  `PORT: 8080:8080`
-- `IMAGE: github-3702` `PORT: 8081:8081`
-- `IMAGE: sum-3702` `PORT: 8082:8082`

- Mesmo depois de parados, os *containers* continuam existindo no sistema. Eles podem ser encontrados através do comando `sudo docker container ls -a` e podem ser apagados com `sudo docker container rm ${CONTAINER ID}`

## Notas Finais

- Não consegui rodar o projeto com WSL 2. O *microservice* Sum ou não era acessível por causa do parâmetro `--network="host"` ou ele não conseguia acessar os *endpoints* *localhost:8080* e *localhost:8081* dos outros dois *microservices*. Só consegui rodar em Linux 'nativo', onde funcionou sem problemas.

- Tive problemas para rodar `sudo docker kill $(docker ps -a -q)` do `run.sh stop` simplesmente com `sudo`, precisei rodar o comando como usuário *ROOT* usando `sudo su` e em sequência o `run.sh stop`, caso tenham problemas de acesso, talvez seja a solução.