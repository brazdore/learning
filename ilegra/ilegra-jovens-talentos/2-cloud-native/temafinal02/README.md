# temafinal02 - SpringBoot/Microservices

## Vídeo Explicativo

- https://youtu.be/_NTzwKbX4Ps

## Requisitos

- JDK 11 ou maior
- Apache Tomcat 9
- Gradle 7
- WAR do Eureka Server
- WAR do Hystrix Dashboard
- JAR do H2 Database Engine

## Preparação (Instalação)

- Instalar e configurar o [JDK](https://jdk.java.net/)
- Instalar e configurar o [Gradle](https://docs.gradle.org/current/userguide/installation.html)
- Baixar o [Tomcat 9](https://tomcat.apache.org/download-90.cgi)
- Baixar o WAR do [Eureka Server](https://search.maven.org/search?q=g:com.netflix.eureka)
- Baixar o WAR do [Hystrix Dashboard](https://mvnrepository.com/artifact/com.netflix.hystrix/hystrix-dashboard/1.5.18)
- Baixar o JAR do [H2 Database Engine](https://search.maven.org/search?q=g:com.h2database)
- Clone este repositório remoto.

## Preparação (Configuração)

> Tomcat

- Copie os 2 arquivos WAR (Eureka Server & Hystrix Dashboard) e cole para a pasta `webapps` do Tomcat
- A partir da pasta raíz do Tomcat execute o comando `./catalina.sh start`

> H2 Database Engine

- A partir da pasta raíz do projeto localize a pasta `.db`
- Copie e cole para o diretório **HOME**:
  -- Windows: `C:/Users/usr/`
  -- Linux: `home/usr/`
- Executar o comando `java -jar {h2}.jar --webPort 9000` no diretório onde o JAR do H2 Database Engine se encontra
- Note que `{h2}` depende do nome do arquivo JAR. Se o nome do JAR é `h2-1.4.200.jar` então o comando será `java -jar h2-1.4.200.jar --webPort 9000`

## Gerando os Gradle Wrappers

- Na pasta raíz do projeto, execute o script `script-gradle-all.sh`

## Executando as aplicações

- Na pasta raíz do projeto, execute o script `script-run-all.sh`
- Este script deve rodar uma instância de cada serviço do projeto; caso queira rodar duas instâncias do `song-service` é necessário alterar a `server.port` em `songservice\src\main\resources\application.properties` e a `eureka.port` em `songservice\src\main\resources\eureka-client.properties` para `8094` e executar o script `script-run-song.sh`

## Eureka Server & Hystrix Dashboard

> Eureka Server

- O Eureka deve estar disponível através do _endpoint_ `http://localhost:8080/{eureka}`
- Note que `{eureka}` depende do nome do arquivo WAR. Se o nome do WAR é `eureka-server-1.10.17`, por exemplo, o endpoint será `http://localhost:8080/eureka-server-1.10.17`

> Hystrix Dashboard

- O Hystrix Dashboard deve estar disponível através do endpoint `http://localhost:8080/{hystrix}`
- Note que {hystrix} depende do nome do arquivo WAR. Se o nome do WAR é `hystrix-dashboard-1.5.18`, por exemplo, o endpoint será `http://localhost:8080/hystrix-dashboard-1.5.18`
- Na página inicial do Dashboard, adicione os endpoints a serem observados:

  -`http://localhost:8091/hystrix.stream`

  -`http://localhost:8092/hystrix.stream`

  -`http://localhost:8093/hystrix.stream`

  -`http://localhost:8094/hystrix.stream` `(OPCIONAL: Caso tenha subido uma segunda instância de song-service)`

- Basta colar estas URLs na caixa de texto e pressionar `Add Stream`.
- Depois que as URLs estiverem adicionadas, clique em `Monitor Streams`.

## Endpoints

> song-service:8091
> **GET**

- `http://localhost:8091/songs/`
- `http://localhost:8091/songs/{id}`
- `http://localhost:8091/songs/{id},{id}...`

> playlist-service:8092
> **GET**

- `http://localhost:8092/playlists/`
- `http://localhost:8092/playlists/{id}`

> app-service:8093
> **GET**

- `http://localhost:8093/app/songs/`
- `http://localhost:8093/app/songs/{id}`
- `http://localhost:8093/app/playlists/`
- `http://localhost:8093/app/playlists/{id}`

> song-service:8094
> **GET**

- `http://localhost:8094/songs/`
- `http://localhost:8094/songs/{id}`
- `http://localhost:8094/songs/{id},{id}...`

## Testes

- É possível testar as aplicações com os scripts `script-test-smoke-all.sh` e `script-test-load-balancer.sh`
- O primeiro testa um endpoint de cada microservice para verificar a conexão
- O segundo faz chamas constantes para `http://localhost:8093/app/playlists/` a fim de testar o balanceamento de carga do `SongRibbon`; este teste só é possível caso pelo menos duas instância do `song-service` estejam rodando simultaneamente

## Notas

- Não consegui subir o JAR do gradle-wrapper; acho que é algo com o `.gitignore` do repositório da ilegra, porque no temafinal01 o repositório base para o projeto é meu e consegui subir o JAR sem problemas. Assim, fui forçado a colocar como requisito uma instalação local do Gradle.
