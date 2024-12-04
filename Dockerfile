# Stage 1: Build the application
FROM gradle:8.11.1-jdk-alpine AS builder
WORKDIR /app
COPY . /app
RUN gradle clean build -x test

# Stage 2: Run the application
FROM openjdk:21
WORKDIR /app
EXPOSE 8100
COPY --from=builder /app/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]