FROM gradle:7.2.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build

FROM openjdk:11-jre-slim
WORKDIR /
COPY --from=build /home/gradle/src/build/libs/github.jar github.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/github.jar", "--server.port=8081"]
