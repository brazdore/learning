# tema10--Packer

## Requisitos

- Packer
- Docker

## Preparação

- Instalar e configurar o [Packer](https://www.packer.io/downloads)
- Instalar e configurar o [Docker](https://docs.docker.com/engine/install/)

## Buildando a imagem

- Na pasta raíz do projeto, execute o comando `packer build packer.json`
- O Packer deve buildar e salvar uma imagem da nossa aplicação no Docker como `go-calc-7621:1.0`

## Rodando um container

- Na pasta raíz do projeto, execute o comando `scripts/docker.sh run`
- É possível checar o status do container executando o comando `scripts/docker.sh status`

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

- É possível testar os endpoints executando o comando `scripts/smoke-test-all.sh` a partir da pasta raíz do projeto
- O script deve criar uma nova pasta `test-output` com os resultados dos testes

## Parando o container

- Na pasta raíz do projeto, execute o comando `scripts/docker.sh stop`
- Novamente, é possível checar o status do container com `scripts/docker.sh status`

## Removendo container & imagem

- A partir da pasta raíz do projeto, execute o comando `scripts/docker.sh clear`
- O script deve remover o container e a imagem criada
