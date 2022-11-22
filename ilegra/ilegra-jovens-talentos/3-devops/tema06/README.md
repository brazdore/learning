# tema06--Golang

## Requisitos

- Golang

## Preparação - Instalação

- Instalar e configurar o [Golang](https://go.dev/)

## Executando a aplicação:

- Na pasta raíz do projeto, execute o comando `./run.sh`
- O script deve criar um módulo, baixar as dependências necessárias e executar a aplicação

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

## Encerrar a aplicação:

- Para parar a aplicação basta um `CTRL+C` no terminal
