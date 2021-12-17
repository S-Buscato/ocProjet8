FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} gpsutil-1.0.0.jar
ENTRYPOINT ["java","-jar","/gpsutil-1.0.0.jar"]
