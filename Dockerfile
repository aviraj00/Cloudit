# First Stage: Build the application
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Maven files first (for Docker cache optimization)
COPY pom.xml .
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Second Stage: Create the final image
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy only the jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
