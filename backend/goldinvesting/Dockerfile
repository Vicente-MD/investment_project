# Define the base image
FROM openjdk:21-jdk as build

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

# Start a new build stage to create a smaller runtime image
FROM openjdk:21-jdk

# Set the working directory in the new image
WORKDIR /app

# Copy the built JAR file from the previous build stage
COPY --from=build /app/target/*.jar /app/application.jar

# Expose the port your application uses
EXPOSE 8080

# Copy wait-for-it script and make it executable
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

# your build commands here
CMD ["./wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "application.jar"]
