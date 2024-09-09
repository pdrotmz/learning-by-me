FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven

WORKDIR /app
COPY . .

RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/LBM-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]


