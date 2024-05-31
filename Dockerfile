# Use an official Gradle image as a build stage
FROM gradle:8.7-jdk21 AS builder

# Set the working directory inside the container
WORKDIR /home/gradle/project

# Copy the Gradle wrapper and settings
COPY gradle/ ./gradle
COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .

# Ensure the Gradle wrapper has executable permissions
RUN chmod +x ./gradlew

# Copy the rest of the project files
COPY src ./src

# Run the Gradle build command
RUN ./gradlew build

# Use a new, smaller image to create the final artifact
FROM openjdk:23-ea-21-jdk-slim-bullseye

# Set the working directory inside the container
WORKDIR /app

# Copy the built application from the builder stage
COPY --from=builder /home/gradle/project/build/libs/*.jar ./app.jar

# Command to run the application
CMD ["java", "-jar", "app.jar"]