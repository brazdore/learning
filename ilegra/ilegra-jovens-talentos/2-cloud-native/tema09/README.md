
# tema09 -- RxNetty/Eureka

## Requisitos

 - JDK 8 ou maior
 - Apache Tomcat 9
 - Eureka
 
## Preparação
- Instalar o [JDK](https://jdk.java.net/)
- Baixar o [Tomcat 9](https://tomcat.apache.org/download-90.cgi)
- Baixar o WAR do [Eureka](https://search.maven.org/search?q=eureka-client)
- Clonar este repositório remoto

## Copiando o Eureka para o Tomcat 9

- Após baixar e instalar ou extrair o Tomcat 9, navegue até sua raíz e localize a pasta `webapps`
- Após baixar o arquivo WAR do Eureka, cole-o na pasta `webapps` do Tomcat 9

## Subindo o Eureka

- A partir da pasta raíz do Tomcat 9, navegue até  `bin`
- Execute o comando  `./catalina.sh start`

##  Acessando o Eureka

- O Eureka deve estar disponível através do *endpoint* `http://localhost:8080/{eureka}`

- Note que `{eureka}` depende do nome do arquivo WAR. Se o nome do WAR é `eureka-server-1.10.17`, por exemplo, o endpoint será `http://localhost:8080/eureka-server-1.10.17`

## Executando os *Microservices*

- A partir da pasta raíz do projeto, executar o comando `./run-all.sh`

- O script executará todos os *microservices*. Como todos eles devem se registrar no Eureka, este processo pode demorar um pouco. É possível acompanhar o progresso com os *logs* gerados na pasta raíz do projeto ou através do endpoint do Eureka no Tomcat 9

## Endpoints

> SUM-SERVICE (2 + 3)

`http://localhost:6004/math/sum/2/3`

> SUB-SERVICE (2 - 3)

`http://localhost:6003/math/sub/2/3`

> MUL-SERVICE ( 2 * 3)

`http://localhost:6002/math/mul/2/3`

> DIV-SERVICE ( 2 / 3)

`http://localhost:6001/math/div/2/3`

> EXP-SERVICE ( 2 ^ 3)

`http://localhost:6009/math/exp/2/3`

---
> CALC-SERVICE: SUM (2 + 3)

`http://localhost:6005/math/calc/sum/2/3`

> CALC-SERVICE: SUB (2 - 3)

`http://localhost:6005/math/calc/sub/2/3`

> CALC-SERVICE: MUL (2 * 3)

`http://localhost:6005/math/calc/mul/2/3`

> CALC-SERVICE: DIV (2 / 3)

`http://localhost:6005/math/calc/div/2/3`

> CALC-SERVICE: EXP (2 ^ 3)

`http://localhost:6005/math/calc/exp/2/3`

> CALC-SERVICE: LIST OPERATIONS

`http://localhost:6005/math/ops`

## Testes

- Os endpoints podem ser testados executando o comando  `./smoke-test-all-microservices.sh` a partir da pasta raíz do projeto
- O script deve imprimir na tela o resultado de algumas operações, testando a conexão com os *microservices*

## Encerrar a execução do Tomcat 9

 - A partir da pasta raíz, navegue até `bin`
 - Execute o comando `./shutdown.sh`

## Notas

- É possível executar os *scripts* `.sh` no Windows 10 utilizando WSL.

- É possível acessar todos os aplicativos deployados no Tomcat através da seção *Manager App* da página padrão do Tomcat em `http://localhost:8080`. Para isto é necessário configurar um usuário em `tomcat-users.xml`.