# ilegra-jovens-talentos

- Este repositório contém todos os projetos e atividades realizados por mim no programa de Jovens Talentos da ilegra entre 13/09/21 e 08/02/22.
- O programa foi dividido em três trilhas: Core Engineering, Cloud Native e DevOps.
- As instruções de cada atividade estão abaixo.
<hr/>

## Core Engineering

### Lean e Agile

#### Tema 2: Agile

```
Faça uma lista com todos os métodos ágeis, incluindo Scrum e XP e explique em 2 ou 3 linhas como eles funcionam:
Faça um texto de 3 – 5 linhas explicando os problemas com agile:
Qual a relação entre Agile/Lean e Bi Modal e DevOps?
```

### OOP

#### Tema 3: override/overload

```
Fazer um texto explicando o que é override e overloading
Enviar via PR
```

### Java

#### Tema 4: Multithreading

```
Criar um programa que use duas threads
A thread A irá gerar apenas números randômicos ímpares e irá printá-los no console. Essa thread deve printar um número a cada 100 milisegundos
A thread B irá gerar apenas números randômicos pares e irá printá-los no console. Essa thread deve printar um número a cada 500 milisegundos
Use a classe main para instanciar e iniciar as Threads
```

### Testes

#### Tema 5: Lâmpada

```
Criar um projeto java com gradle
Esse projeto deverá tem  uma Interface Lampada com a assinatura dos método void on(); e void off().
Criar duas classe que implementam essa interface
Criar uma classe que recebe uma lâmpada pelo construtor e expor um método void switchState()
Na classe main, exemplificar o exercício
Testar o método switchState para cada cenário ON e OFF
Testar a classe que tem o método switch com cada uma das classes que implementam a interface Lampada
Observação: utilizar JUNIT5
```

### Clean Code

#### Tema 6: Agenda de contatos

```
Criar um projeto de agenda de contato
Deve ser possível adicionar contato, remover contato, listar contato, buscar por nome, buscar por id.
Não utilizar banco de dados e nem arquivos. Usem um array em memória.
Adicione testes unitários para cada operação
Fazer PR
```

### TDD

#### Tema 7: TDD

```
Criar um conversor de números romanos para números inteiros
Deve suportar números de 1 a 20
Deve ser possível passar um numero romano e convertê-lo para inteiro
Deve ser possível também passar um numero inteiro e converte-lo para romano
Deve ser desenvolvido utilizando TDD. Testes primeiro.
```

### Philosophy of Software Design

#### Tema 8: Biblioteca

```
Fazer uma aplicação de empréstimo livro
Deve ser possível
Cadastrar livro (id, titulo, autor)
Listar livro
Excluir (não pode excluir um livro que está emprestado)
Buscar por título ou por autor
Empréstimo de livro
Uma pessoa pode fazer empréstimo de no máximo 5 livros
Prazo de entrega é 7 dias
Uma pessoa pode devolver vários livros
Uma pessoa pode renovar um livro por mais 7 dias, exceto se ela estiver atrasada
No momento da devolução será cobrado 5 reais por dia de atraso
Exibir relatório de livros emprestados e nome da pessoa que estiver com ele
Exibir os TOP 10 usuários que mais locam livros
Exibir relatórios do cliente que atrasaram a entrega e quantos dias de atraso
Independente da abordagem TDD ou testes depois, deve ter testes
Não pode ter banco de dados, salvar tudo em arquivo
```

### DDD

#### Tema 9 : DDD

```
Responder as seguintes questões:
Qual é o objetivo principal do DDD?
Qual é o impacto que DDD pode causar no projeto?
```

### Banco de dados

#### Tema 10: Agenda de contato + banco de dados

```
Criar um projeto de agenda de contato (id, nome, telefone, email)
Deve ser possível adicionar contato, remover contato, listar contato (ordenar por nome/id), buscar por nome, buscar por id.
Todas as operações devem envolver banco de dados.
Fazer PR
```

### Design Pattern (criacionais)

#### Tema 11: Factory

```
Implementar o padrão de projeto Factory com tema livre
```

#### Tema 12: Builder

```
implementar builder com interface fluente
Crie uma classe Pessoa (nome, data de nascimento, endereço),
Crie uma classe endereço (rua, número, cidade, estado)
Crie uma classe builder com interface fluente para fazer a criação de uma pessoa completa.
```

### Design Pattern (comportamentais)

#### Tema 13: Strategy

```
Criar um sistema de envio notificação que suporta apenas SMS e Email (apenas escrever no terminal o tipo de notificação junto com a mensagem)
Deve existir uma classe que represente um cliente apenas com nome,email e telefone.
Deve existir uma classe responsável por apenas montar uma mensagem(Hello + nome do cliente) e usar uma classe de notificação para enviar.
Utilizar o padrão Strategy para definir SMS e Email.
Regra: o sistema deve validar se o cliente possui  telefone preenchido enviar por telefone caso contrario enviar por email(email é obrigatório).
```

#### Tema 14: Template method

```
criar um sistema responsável por fazer bebida quentes
criar uma abstração para definir os métodos necessários(abstratos) para criar uma bebida quente e um método(concreto) responsável por executar em ordem
Criar duas classes para criar bebidas quente .
```

#### Tema 15: Chain

```
Crie uma classe abstrata NotificationHandler
Crie 3 implementações dessa classes(Exemplo: EmailNotificationHandler)
Crie uma classe Request
A partir de uma request pode executar mais de um elemento da chain
```

#### Tema 16: Command

```
Crie um sistema de banco onde é possível apenas depositar dinheiro e sacar dinheiro
Essas duas ações devem utilizar o design pattern command
Deve ser possível salvar o histórico os commands executados, usem um arraylist static
Deve existir consulta de saldo que irá ler todos os commands para calcular o saldo
```

### Design Pattern (estruturais)

#### Tema 17: Decorator

```
Crie uma classe que será a base de uma pizza (preço 20 reais)
Crie um decorador para adicionar queijo (preço 3 reais)
Crie um decorator para adicionar bacon (preço 3,5)
No main, use as classe criadas para montar uma pizza de bacon com o dobro de queijo. Qual é o valor da pizza?
```

<hr/>

## Cloud Native

### Spring Framework

#### Tema 1 - Calculadora com spring

```
1) Criar calculadora com anotações
 cada função modular
 operações SUM/SUB/MULTIPLY/DIVISION/POW
 Histórico
2) Validações
3) Fazer testes
```

### Cloud Native Review / Guice / Tomcat / Jetty

#### Tema 2 - Petstore Guice

```
Create a petstore providing:
 Add pets(id, name, race, age)
Remove pet’s(id)
Search pet by Age
doBath(id) with or without perfume / dry or water
doHairCut(id) short or long
history services
top 10 pets revenue
```

#### Tema 3 - Tomcat

```
Choose the petstore or calculator to deploy on Tomcat.
The steps to run the application must be on readme file.
```

#### Tema 4 - Jetty

```
Choose the petstore or calculator to deploy on Jetty.
The steps to run the application must be on readme file.
```

### SOA Principles

### Rest

#### Tema 5 - Pedágio Rest

```
Criar um sistema de pedagio usando REST
onibus 1,59
moto 1
bike 49
caminhão 3,95
Fusca 2,11
Pagamento: Deve receber um valor e retornar o troco. Caminhão deve cobrar adicional por eixo.
Listar a tabela de preços do pedágio
```

### Docker

#### Tema 6 - Containerizar a calculadora

```
Criar um container docker com a aplicação da calculadora desenvolvidas e acessar a mesma pelo browser.
```

#### Tema 7 - Criar microservices

```
Criar microservice que pelo user busca o total de tweet (usar api twitter)
Criar microservice que pelo user busca o total de repositórios no Github(usar api github)
Criar microservice que recebe usuario twitter e usuario do github e retorno os totalizadores (chamar os outros serviços)
Rodar tudo dentro de containers docker
Utilizar SpringBoot
```

### Rx and RxNetty

#### Tema 8 - RX and RxNetty

```
Migrar a calculadora para Karyon RxNetty
Rodar em container
```

### NetflixOss Stack

#### Tema 9 - Microservicos

```
implementar POW microservice
https://github.com/diegopacheco/netflixoss-pocs/tree/master/netflixoss-microservices
```

### Hystrix, Dasboard and NoSql

#### Tema 10 - hystrix

```
Adicionar hystrix e o dashboard no tema do springboot
Não utilizar SpringCloud!
```

## Temas Finais de Cloud Native

### [Tema Final #1](2-cloud-native/temas/tema_final_1.pdf)

### [Tema Final #2](2-cloud-native/temas/tema_final_2.pdf)

<br>
<hr/>

## DevOps

#### Homework 1-DevOps.Culture.Principles

```
1. IF I'm doing deploy automation? I'm doing devops? Explain why is true or false?
2. How do we improve the reliability of a system?(Tip: Look for SRE)
3. Reaserch about Postmortem and list the basic elements of a post-mortem analysis/report.
```

#### Homework 2-SOA.Microservices.Cloud-Native

```
1. Why SOA/Microservices is important for DevOps Engineering?
2. If I run my software in containers in bare-metal could I consider my architecture cloud-native? Why?
3. Explain how the circuit-breaker pattern works.
```

#### Homework 3-Linux.pdf

```
1. What the Cron services does in linux? how to use it?
2. What is SystemD? How to use it?
3. Why linux is so important to DevOps? Explain Why?
```

#### Homework 4-Bash

```
1. Create a calculator in bash(+, -, *, /) using functions and switch.
2. Create a bash script that ZIP the ALL content of a folder and move to a folder /backup/data/$TODAY/
3. Create a bash script that LIST of ENV_VARS in linux, store to a file called env_data.txt located at /backup/conf/$TODAY/
```

#### Homework 5-Python

```
1. Create a python HTTP service that list all linux env_vars on the http endpoint localhost:8080/conf/env
2. Create a python script that recives a 2 parameter env_name, env_Var via HTTP and create a linux env_var. Endpoint: localhost:8080/env/$name/$v
3. Create a python script that list all running software(PID and cmd) send the list to a slack channel.
```

#### Homework 6-GO

```
1. Make a simple microservice using go. This should be a calculator Microservice where
   you need to have one endpoit per operation .ie:
   /calc/sum/$a/$b
   /calc/sub/$a/$b
   /calc/mul/$a/$b
   /calc/div/$a/$b
  We also need to have a operation which show all previous operations like:
   /calc/history
```

#### Homework 7-Docker

```
1. Create a Dockerfile for your go microservice
2. Create a BASH script that runs your microservice and stop it show the status - we should use functions. ie:
   startMicroservice()
   stopMicroservice()
   statusMicroservice() = RUNNING | NOT RUNING
```

#### Homework 8-Vagrant

```
1. Create a Vagrantfile in order to provision(using shell) your microserivce and go.
2. Send you start-up script to vagrant machine as well.
```

#### Homework 9-Ansible

```
1. Using proper ROLES/Structure in Ansbile provision your go microservice in ansible.
   You need to have generic ROLEs sucjs as:
    - base (where you will update the os and install commons packages - could be ubuntu or centos)
    - go (proper install go)
2. Create a BASH script using ansible to check if your go microservice is running(PID check)
```

#### Homework 10-Packer

```
1. Create a packaer config file for backing a docker image of your go microservice.
```

#### Homework 11-Terraform

```
1. Research about Terraform and Ansible. Explain the differences and when use one or another.
2. You will have a pratical terraform code on final chapter :D
```

#### Homework 12-Jenkins

```
1. Create a 2 PIPELINEs for your GO Microservice
   1.1 First pipeline will be caled BAKE and you will need use PACKER and bake a docker image
   1.2 Second pipeline will be called LAUNCH and you will DEPLOY you microservice in DOCKER
```

#### Homework 13-AWS

```
1. Create a machine t2.micro on AWS and install your go microservice there
2. Create an ELB and point to your EC2 machine
3. Bake an AWS image with packer of your go microservice
4. Create one ASG/LC for you microservice using the image you bake
5 (BONUS) launch ASG/LC/ELB using Terraform
```
