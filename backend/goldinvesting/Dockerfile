# Use the official OpenJDK image for the JDK base
FROM openjdk:21-jdk-slim as build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven configuration file and source code to the image
COPY pom.xml /app
COPY src /app/src
COPY mvnw /app
COPY .mvn /app/.mvn

# Make sure mvnw is executable
RUN chmod +x mvnw

# Package the application
RUN ./mvnw clean package
