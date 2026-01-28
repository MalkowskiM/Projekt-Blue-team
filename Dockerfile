# ---- Build Stage ----
# Use Maven base image from the Docker Hub
FROM maven:3.8.3-openjdk-11-slim AS build

# Set the current working directory inside the image
WORKDIR /app

# Copy the source code to the container
COPY src /app/src
COPY pom.xml /app

# Package the application
RUN mvn clean install -DskipTests

# ---- Deploy Stage ----
FROM openjdk:26-ea-11-jdk-slim


RUN groupadd -r spring && useradd -r -g spring spring

COPY --from=build --chown=spring:spring /app/target/thymeleaf-0.0.1-SNAPSHOT.jar /app.jar
USER spring
# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]