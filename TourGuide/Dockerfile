FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} tourguide-v1.jar
ENTRYPOINT ["java","-jar","/tourguide-v1.jar"]