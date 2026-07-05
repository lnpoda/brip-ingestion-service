FROM openjdk:22-jdk-slim AS build

WORKDIR /app

COPY ./build/libs/*-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]