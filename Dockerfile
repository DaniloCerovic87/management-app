FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/management-app-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "management-app-0.0.1-SNAPSHOT.jar"]