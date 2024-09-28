FROM openjdk:17-jdk-alpine

RUN apk add --no-cache curl bash && \
    curl -o /usr/local/bin/mvn https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.8.6/apache-maven-3.8.6-bin.zip && \
    chmod +x /usr/local/bin/mvn

WORKDIR /app

COPY . .

RUN ./mvnw clean install -DskipTests || mvn clean install -DskipTests


COPY target/management-app-0.0.1-SNAPSHOT.jar /app/management-app.jar

CMD ["java", "-jar", "/app/management-app.jar"]
