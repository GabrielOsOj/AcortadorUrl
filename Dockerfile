FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/acortadorUrl-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} urlShortener.jar

EXPOSE 8080
ENTRYPOINT [ "java","-jar","urlShortener.jar" ]