FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/management-app-0.0.1-SNAPSHOT.jar /app/management-app.jar

CMD ["java", "-jar", "/app/management-app.jar"]
