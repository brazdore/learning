# tema09--Ansible

## Requisitos

- Vagrant
- Vagrant Provider

## Preparação

- Instalar e configurar o [Vagrant](https://www.vagrantup.com/)
- Instalar e configurar um [Vagrant Provider](https://www.vagrantup.com/docs/providers)

## Executando a aplicação:

- Na pasta raíz do projeto, execute o comando `vagrant up`

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

- Para liberar o terminal basta um `CTRL + C`
- É possível entrar na VM executando o comando `vagrant ssh`
- O script criado pelo Ansible deve estar em `/home/vagrant/shared/main/scripts/status.sh`

## Testes

- É possível testar os endpoints executando o comando `main/scripts/smoke-test-all.sh` a partir da pasta raíz do projeto OU executando este mesmo script em `/home/vagrant/shared/main/scripts/smoke-test-all.sh` na VM
- O script deve criar uma nova pasta `test-output` com os resultados dos testes

- É possível verificar o status da aplicação executando o script em `/home/vagrant/shared/main/scripts/status.sh` na VM

## Encerrar a aplicação

- Para parar a aplicação basta um `sudo kill -9 ${PID}`
- O PID da aplicação pode ser descoberto ao executar o script `status.sh`

## Parar & destruir VM do Vagrant

- Para parar a VM, execute o comando `vagrant halt`
- Para destruir a VM, execute o comando `vagrant destroy`
