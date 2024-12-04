FROM gradle:5.3.0-jdk-alpine
RUN gradle clean build -x

FROM openjdk:21
EXPOSE 8100
COPY ./build/libs/*.jar app.jar
CMD ["java","-jar","app.jar"]