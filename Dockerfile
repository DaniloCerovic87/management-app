FROM openjdk:17-jdk-alpine

RUN apk add --no-cache bash curl && \
    curl -L -o maven.tar.gz https://www-eu.apache.org/dist/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz && \
    tar xzvf maven.tar.gz -C /opt/ && \
    ln -s /opt/apache-maven-3.8.6/bin/mvn /usr/bin/mvn && \
    rm -rf maven.tar.gz

WORKDIR /app

RUN ./mvnw clean install -DskipTests || mvn clean install -DskipTests


COPY target/management-app-0.0.1-SNAPSHOT.jar /app/management-app.jar

CMD ["java", "-jar", "/app/management-app.jar"]
