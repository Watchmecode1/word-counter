FROM openjdk:21
ARG JAR_FILE=target/word-counter-1.0.0.jar
COPY ${JAR_FILE} word-counter.jar
ENTRYPOINT ["java", "-jar", "/word-counter.jar"]
EXPOSE 8080