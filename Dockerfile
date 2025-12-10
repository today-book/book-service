FROM eclipse-temurin:21-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar", "app.jar", "--spring.profiles.active=prod"]

EXPOSE 3000