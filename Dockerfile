# Use OpenJDK as the base image
FROM eclipse-temurin:21
# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file (Make sure you have built your project first)
COPY target/smart_contact_manager-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
