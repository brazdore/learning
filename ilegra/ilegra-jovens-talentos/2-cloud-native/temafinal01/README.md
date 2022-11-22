# temafinal01 -- DevOps Pipeline

## Informações

- Repositório: `https://github.com/brazdore/ilegra-cn-temafinal01.git`
- Vídeo: `https://www.youtube.com/watch?v=FnpFRo1KWI8`

## Requisitos

 - Jenkins
 - JFrog Artifactory
 - Docker
 - Packer
 - JDK 11 ou maior
 - Conta no Docker Hub
 
## Preparação [Instalação]
- Instalar o [Jenkins](https://www.jenkins.io/doc/book/installing/)
- Instalar o [JFrog Arficatory](https://www.jfrog.com/confluence/display/JFROG/Install)
- Instalar o [Docker](https://docs.docker.com/engine/install/)
- Instalar o [Packer](https://learn.hashicorp.com/tutorials/packer/get-started-install-cli)
- Instalar o [JDK](https://jdk.java.net/)
- Crie uma conta no [Docker Hub](https://hub.docker.com/)

## Preparação [Configuração]

> Jenkins [Primeiro acesso]
- Rode o Jenkins
- Acesse `http://localhost:8080` e crie as credenciais
- Instale os plugins recomendados

> JFrog Artifactory [Primeiro acesso & repositório]
-  Rode o JFrog Artifactory
- Acesse `http://localhost:8082` e crie as credenciais
- Crie um novo repositório em `Repositories > Add Repositories > Local Repository` com:
  - **Repository Key** como `ilegra-cn-temafinal01` 
  - **Package Type** como `Gradle`

> Docker Hub [Repositório e token]
- Acesse a conta do Docker Hub
- Crie um novo repositório
- Em `Account Settings > Security` crie um novo token com permissões de `Read, Write, Delete`
- Salve o token

> Jenkins [JFrog]
- Em `Manage Jenkins > Manage Plugins > Avaliable` instale o `Artifactory Plugin`
- Em `Manage Jenkins > Manage Plugins` procure a seção do JFrog
- Em `Add JFrog Plataform Instance` configure uma nova instância do JFrog com **Instance ID** de `jfrog-final-task` e **URL** como `http://localhost:8082`
- Na seção de `Default Deployer Credentials` passe as credenciais do JFrog Artifactory
- Se tudo deu certo o `Test Connection` deve voltar algo como `Found JFrog Artifactory X.XX.XX at http://localhost:8082/artifactory`

> Jenkins [Docker Hub]
- Em `Manage Jenkins > Manage Credentials > (global) > Add Credentials` crie uma nova credencial com:
  - **Kind** de `Username with password`
  - **ID** de `dockerhub-cred`
  - **Username** com o *username* do Docker Hub 
  - **Password** com o token gerado do Docker Hub
  
> Repositório do job02/Jenkinsfile
- É necessário alterar a variável `DOCKERHUB_REPOSITORY_NAME` em `job02/Jenkinsfile` para o nome do repositório criado no Docker Hub
- O padrão é `username/repositoryName` como `brazdore/ilegra-cn-temafinal01`

> Pull & Run do job03/Jenkinsfile
- Em job03/Jenkinsfile altere `sh 'docker pull brazdore/ilegra-cn-temafinal01:temafinal01'` para o nome do repositório criado no Docker Hub, por exemplo, `sh 'docker pull username/repositoryName:temafinal01'`

- Também é necessário alterar o `sh 'docker run -p 8089:8080 -d brazdore/ilegra-cn-temafinal01:temafinal01'` para o nome do repositório criado no Docker Hub, por exemplo, `sh 'docker run -p 8089:8080 -d username/repositoryName:temafinal01'`

> Docker Daemon Socket
- No terminal, execute o comando `sudo chmod 666 /var/run/docker.sock`
- Este evita os problemas descritos neste [post](https://www.digitalocean.com/community/questions/how-to-fix-docker-got-permission-denied-while-trying-to-connect-to-the-docker-daemon-socket) e é necessário para o job02


## job01-build

- Na Dashboard do Jenkins clique em `New Item`; nomeie de `job01-build` e selecione `Pipeline`
- Na seção de `Pipeline` selecione **Definition** como `Pipeline script from SCM`
- Em **SCM** selecione `Git` e passe como **Repository URL** `https://github.com/brazdore/ilegra-cn-temafinal01.git`
- Em **Branch Specifier** certifique-se que esteja `*/master`
- Em **Script Path** passe `job01/Jenkinsfile`
- De volta ao Dashboard, rode a pipeline criada
- Se tudo deu certo nosso repositório do JFrog deve conter um arquivo com o nome `ilegra-cn-temafinal01.war`

## job02-infrastructure

- Na Dashboard do Jenkins clique em `New Item`; nomeie de `job02-infrastructure` e selecione `Pipeline`
- Na seção de `Pipeline` selecione **Definition** como `Pipeline script from SCM`
- Em **SCM** selecione `Git` e passe como **Repository URL** `https://github.com/brazdore/ilegra-cn-temafinal01.git`
- Em **Branch Specifier** certifique-se que esteja `*/master`
- Em **Script Path** passe `job02/Jenkinsfile`
- De volta ao Dashboard, rode a pipeline criada
- Se tudo deu certo nosso repositório do Docker Hub deve conter uma imagem com a **tag** `temafinal01`

##  job03-run

- Na Dashboard do Jenkins clique em `New Item`; nomeie de `job03-run` e selecione `Pipeline`
- Na seção de `Pipeline` selecione **Definition** como `Pipeline script from SCM`
- Em **SCM** selecione `Git` e passe como **Repository URL** `https://github.com/brazdore/ilegra-cn-temafinal01.git`
- Em **Branch Specifier** certifique-se que esteja `*/master`
- Em **Script Path** passe `job03/Jenkinsfile`
- De volta ao Dashboard, rode a pipeline criada
- Se tudo deu certo o comando `docker container ls` deve listar um container com **PORTS** como `8089->8080`

## Endpoints
```
Exemplos GET
```
> Adição (2 + 3)

`localhost:8089/ilegra-cn-temafinal01/calc/add/2/3`

> Subtração (2 - 3)

`localhost:8089/ilegra-cn-temafinal01/calc/sub/2/3`

> Multiplicação ( 2 * 3)

`localhost:8089/ilegra-cn-temafinal01/calc/mul/2/3`

> Divisão ( 2 / 3)

`localhost:8089/ilegra-cn-temafinal01/calc/div/2/3`

> Exponenciação ( 2 ^ 3)

`localhost:8089/ilegra-cn-temafinal01/calc/exp/2/8`

> Histórico

`localhost:8089/ilegra-cn-temafinal01/calc/history`
```
Exemplo POST
```
> Apagar Histórico

`localhost:8089/ilegra-cn-temafinal01/calc/clear`


## Encerrar & Remover container

- Execute o comando `docker container ls` e copie o ID do container
- Execute o comando  `docker stop ${CONTAINER ID}`
- Execute o comando  `docker rm ${CONTAINER ID}`

## Notas

- Foi necessário alterar o `gradlew` com o comando `chmod +x gradlew` para evitar o problema descrito neste [post](https://stackoverflow.com/questions/17668265/gradlew-permission-denied).

- O comando `sudo chmod 666 /var/run/docker.sock` parece ser necessário em toda nova inicialização do Docker.

- As URLs do Jenkins e do JFrog Artifactory, i.e., http://localhost:8080 e http://localhost:8082 supõe uma instalação padrão.