FROM gradle:7.2.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build

FROM openjdk:11-jre-slim
WORKDIR /
COPY --from=build /home/gradle/src/build/libs/twitter.jar twitter.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/twitter.jar", "--server.port=8080"]