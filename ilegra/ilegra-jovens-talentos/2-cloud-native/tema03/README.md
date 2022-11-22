# tema03 - Cloud Native

## Requisitos

 - JDK 8.0 ou maior
 - Apache Tomcat 9
 - Gradle 7.2
 
## Preparação
- Instalar o [JDK](https://jdk.java.net/).
- Instalar o [Gradle](https://docs.gradle.org/current/userguide/installation.html).
- Baixar o [Tomcat 9](https://tomcat.apache.org/download-90.cgi).
- Baixe este repositório remoto.

## Gerando o WAR

 - Na pasta raíz do projeto, execute o comando `gradle wrapper`
 - Na pasta raíz do projeto, execute o comando `./gradlew clean build`

## Copiando o WAR para Tomcat

 - A partir da pasta raíz do projeto, navegue até `build\libs` e copie o arquivo `tema03-1.0.war`
 - Navegue até a pasta raíz do Tomcat e cole o WAR para a pasta `webapps`


## Rode o Tomcat

> Windows

 - A partir da pasta raíz, navegue até `bin`
 - Execute o comando `./catalina.bat start`

> Linux

 - A partir da pasta raíz, navegue até `bin`
 - Execute o comando `./catalina.sh start`

## Endpoints

Todos os endpoints do projeto devem estar disponíveis a partir de `localhost:8080/tema03-1.0/calc`

	Exemplos GET

> Adição (2 + 3)

`localhost:8080/tema03-1.0/calc/add?x=2&y=3`

> Subtração (2 - 3)

`localhost:8080/tema03-1.0/calc/sub?x=2&y=3`

> Multiplicação ( 2 * 3)

`localhost:8080/tema03-1.0/calc/mul?x=2&y=3`

> Divisão ( 2 / 3)

`localhost:8080/tema03-1.0/calc/div?x=2&y=3`

> Exponenciação ( 2 ^ 3)

`localhost:8080/tema03-1.0/calc/add?x=2&y=3`

> Histórico

`localhost:8080/tema03-1.0/calc/history`

    Exemplo POST

> Apagar Histórico

`localhost:8080/tema03-1.0/calc/clear`




## Encerrar execução do Tomcat

> Windows

 - A partir da pasta raíz, navegue até `bin`
 - Execute o comando `./shutdown.bat`

> Linux

 - A partir da pasta raíz, navegue até `bin`
 - Execute o comando `./shutdown.sh`