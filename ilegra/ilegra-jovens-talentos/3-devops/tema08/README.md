# tema08--Vagrant

## Requisitos

- Vagrant
- Vagrant Provider

## Preparação

- Instalar e configurar o [Vagrant](https://www.vagrantup.com/)
- Instalar e configurar um [Vagrant Provider](https://www.vagrantup.com/docs/providers)

## Executando a aplicação:

- Na pasta raíz do projeto, execute o comando `vagrant up`
- O Vagrant deve criar uma VM com Ubuntu 18.04 e executar os scripts de instalação do Golang e de execução da aplicação

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

## Vagrant SSH

- É possível entrar na VM executando o comando `vagrant ssh`
- Dentro da VM, `ls` deve retornar 1 diretório e 1 arquivo: `go-calc` e `vagrant`

## Testes

- É possível testar os endpoints executando o comando `scripts/smoke-test-all.sh` a partir da pasta raíz do projeto
- O script deve criar uma nova pasta `test-output` com os resultados dos testes

## Encerrar a aplicação

- Para parar a aplicação basta um `CTRL+C` no terminal

## Parar & destruir VM do Vagrant

- Para parar a VM, execute o comando `vagrant halt`
- Para destruir a VM, execute o comando `vagrant destroy`
