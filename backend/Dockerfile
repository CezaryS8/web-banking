# Use a base image that supports Java 17 with Maven
FROM maven:3.8.4-openjdk-17 as build

# Set the working directory in the Docker container
WORKDIR /app

# Copy the Maven build file
COPY pom.xml .

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Start new stage for the final image
FROM openjdk:17

# Set the working directory in the Docker container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/web-banking-0.0.1-SNAPSHOT.jar /app/web-banking-0.0.1-SNAPSHOT.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/web-banking-0.0.1-SNAPSHOT.jar"]
