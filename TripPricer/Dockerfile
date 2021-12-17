FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} tripPricer-1.0.0.jar
ENTRYPOINT ["java","-jar","/tripPricer-1.0.0.jar"]
