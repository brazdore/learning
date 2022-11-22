# tema07--Docker

## Requisitos

- Docker

## Preparação - Instalação

- Instalar e configurar o [Docker](https://docs.docker.com/engine/install/)

## Executando o container:

- Na pasta raíz do projeto, execute o comando `./app.sh start`
- O script deve buildar a imagem e subir um container com todas as dependências necessárias
- É possível checar o status do container executando o comando `./app.sh status`

## Endpoints

> **GET**

- `http://localhost:8080/calc/<op>/<x>/<y>`

  - Retorna o resultado da operação entre X e Y

- `http://localhost:8080/calc/history`

  - Retorna o histórico de operações

- `http://localhost:8080/calc/clear`

  - Limpa o histórico de operações

- `http://localhost:8080/headers`

  - Retorna os headers da requisição

> **Exemplos:**

- `http://localhost:8080/calc/sum/2/3`

  - 2 + 3

- `http://localhost:8080/calc/sub/10/2`

  - 10 - 2

- `http://localhost:8080/calc/mul/8/8`

  - 8 \* 8

- `http://localhost:8080/calc/div/5/2.5`

  - 5 / 2.5

- `http://localhost:8080/calc/exp/2/8`
  - 2 ^ 8

## Testes

- É possível testar os endpoints com executando o comando `./smoke-test-all.sh` a partir da pasta raíz do projeto
- O script deve criar uma nova pasta `test-output` com os resultados dos testes

## Parando o container

- A partir da pasta raíz do projeto, execute o comando `./app.sh stop`
- Novamente, é possível checar o status do container com `./app.sh status`

## Removendo container & imagem

- A partir da pasta raíz do projeto, execute o comando `./app.sh clear`
- O script deve remover o container e a imagem criada
