# tema12--Jenkins

## Informações

- Repositório base: `https://github.com/brazdore/ilegra-devops-tema12.git`

## Requisitos

- Jenkins
- Docker
- Packer
- JDK 11 ou maior

## Preparação

- Instalar o [Jenkins](https://www.jenkins.io/doc/book/installing/)
- Instalar o [Docker](https://docs.docker.com/engine/install/)
- Instalar o [Packer](https://learn.hashicorp.com/tutorials/packer/get-started-install-cli)
- Instalar a [JDK](https://www.oracle.com/java/technologies/downloads/)

## Configuração:

> Jenkins: Primeiro acesso

- Rode o Jenkins
- Acesse `localhost:8080` e crie as credenciais
- Instale os plugins recomendados

> Docker Daemon Socket

- No terminal, execute o comando `sudo chmod 666 /var/run/docker.sock`
- Este evita os problemas descritos neste [post](https://www.digitalocean.com/community/questions/how-to-fix-docker-got-permission-denied-while-trying-to-connect-to-the-docker-daemon-socket) e é necessário para o job02

> Jenkins: job01-Bake

- Na Dashboard do Jenkins clique em `New Item`
- Nomeie de `job01-bake` e selecione `Pipeline`
- Selecione **Advanced Project Options**
- Na seção de `Pipeline` selecione **Definition** como `Pipeline script from SCM`
- Em **SCM** selecione `Git` e passe como **Repository URL** `https://github.com/brazdore/ilegra-devops-tema12.git`
- Em **Branch Specifier** certifique-se que esteja `*/master`
- Em **Script Path** passe `bake/Jenkinsfile`
- De volta ao Dashboard, rode a pipeline criada
- Se tudo deu certo nosso Docker deve ter pelo menos duas imagens: `golang` e `go-calc-3891:1.0`

> Jenkins: job02-Launch

- Mesmos passos do job01, mas o valor do **Script Path** será launch/Jenkinsfile
- Rode a pipeline criada
- Se tudo deu certo nosso Docker deve ter pelo menos um container rodando: `gontainer74`
- É necessário que a porta `8088` esteja livre

## Endpoints

> **GET**

- `http://localhost:8088/calc/<op>/<x>/<y>`
  - Retorna o resultado da operação entre X e Y
- `http://localhost:8088/calc/history`
  - Retorna o histórico de operações
- `http://localhost:8088/calc/clear`
  - Limpa o histórico de operações
- `http://localhost:8088/headers`
  - Retorna os headers da requisição

> **Exemplos:**

- `http://localhost:8088/calc/sum/2/3`
  - 2 + 3
- `http://localhost:8088/calc/sub/10/2`
  - 10 - 2
- `http://localhost:8088/calc/mul/8/8`
  - 8 \* 8
- `http://localhost:8088/calc/div/5/2.5`
  - 5 / 2.5
- `http://localhost:8088/calc/exp/2/8`
  - 2 ^ 8

## Testes

- É possível testar os endpoints executando o comando `scripts/smoke-test-all.sh` a partir da pasta raíz do projeto
- O script deve criar uma nova pasta `test-output` com os resultados dos testes

## Parando o container

- Para parar o container Docker, execute o comando `scripts/docker.sh stop` a partir da pasta raíz do projeto
- É possível checar o status do container com o comando `scripts/docker.sh status`

## Removendo container & imagem

- A partir da pasta raíz do projeto, execute o comando `scripts/docker.sh clear`
- O script deve remover o container e a imagem criada

## Notas

- O Jenkins pode ser gerenciado através dos comandos `service jenkins ${command}` ou `systemctl ${command} jenkins`
- Alguns dos comandos disponíveis: `start`; `stop`; `status`; `restart` ; `force-reload`

- O comando `sudo chmod 666 /var/run/docker.sock` parece ser necessário em toda nova inicialização do Docker.
