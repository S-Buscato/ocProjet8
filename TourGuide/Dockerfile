FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} tourGuide-1.0.0.jar
ENTRYPOINT ["java","-jar","/tourGuide-1.0.0.jar"]
