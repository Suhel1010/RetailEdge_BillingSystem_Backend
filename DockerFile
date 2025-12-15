# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Set working directory inside the build application
WORKDIR /app

# Copy pom.xml and download dependencies (cache boost)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire source code
COPY src ./src

# Build the jar file (skip test for faster build)
RUN mvn clean package -DskipTests

#  ------------- Stage 2 ----------------------------

FROM eclipse-temurin:21-jdk-jammy

# Set working directory inside the runtime application
WORKDIR /app

# Copy build jar from previous stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port you Spring Boot run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","app.jar"]

