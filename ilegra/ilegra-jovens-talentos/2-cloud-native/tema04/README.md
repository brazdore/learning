# tema04 - Jetty

## Requisitos

- JDK 11 ou maior
- Eclipse Jetty 10
- Gradle 7.2

## Preparação

- Instalar e configurar o [JDK](https://www.oracle.com/java/technologies/downloads/#java11).
- Instalar e configurar o [Gradle](https://docs.gradle.org/current/userguide/installation.html).
- Baixar o [Jetty 10](https://www.eclipse.org/jetty/download.php).
- Clonar este repositório.

## Preparando o Jetty

- Após baixar o Jetty, extraia o arquivo compactado para qualquer local. Este diretório será o `JETTY-HOME`.
- Crie uma pasta em qualquer local. Este diretório será o `JETTY-BASE`.
- No diretório `JETTY-HOME` execute o comando abaixo, passando como variável o caminho completo do
  diretório `JETTY-BASE`:

> `java -jar start.jar jetty.base={PATH} --add
> module=server,http,deploy,annotations`

- Se tudo deu certo, o diretório `JETTY-BASE` deverá ter três novas pastas, incluindo `webapps`.

## Gerando o WAR

- Na pasta raíz do projeto, execute o comando `gradle wrapper`
- Na pasta raíz do projeto, execute o comando `./gradlew clean build`

## Copiando o WAR para o Jetty

- A partir da pasta raíz do projeto, navegue até `build/libs` e copie o arquivo `tema04-1.0.war`
- Navegue até o diretório `JETTY-BASE` e cole o WAR para a pasta `webapps`

## Rode o Jetty

- A partir do diretório `JETTY-HOME`, execute o comando abaixo passando como variável o caminho completo até o
  diretório `JETTY-BASE`:

> `java -jar start.jar jetty.base={PATH}`

## Endpoints

Todos os endpoints do projeto devem estar disponíveis em `localhost:8080/tema04-1.0/calc`

	Exemplos GET

> Adição (2 + 3)

`http://localhost:8080/tema04-1.0/calc?op=add&x=2&y=3`

> Subtração (2 - 3)

`http://localhost:8080/tema04-1.0/calc?op=sub&x=2&y=3`

> Multiplicação (2 * 3)

`http://localhost:8080/tema04-1.0/calc?op=mul&x=2&y=3`

> Divisão (2 / 3)

`http://localhost:8080/tema04-1.0/calc?op=div&x=2&y=3`

> Exponenciação (2 ^ 3)

`http://localhost:8080/tema04-1.0/calc?op=exp&x=2&y=3`

> Histórico

`http://localhost:8080/tema04-1.0/calc?op=history`

## Encerrar execução do Jetty

- Para encerrar a execução do Jetty basta dar um `CTRL + C` no terminal.