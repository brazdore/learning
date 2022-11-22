FROM gradle:7.2.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build

FROM jetty:10.0.7-jre11-slim
COPY --from=build /home/gradle/src/build/libs/tema06.war /var/lib/jetty/webapps/
EXPOSE 8080