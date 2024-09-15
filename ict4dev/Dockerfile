FROM openjdk:22

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ict4dev-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/ict4dev-0.0.1-SNAPSHOT.jar"]