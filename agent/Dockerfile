FROM openjdk:11-slim
VOLUME /tmp
COPY build/libs/agent-*.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
