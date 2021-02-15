FROM openjdk:8-jdk-alpine
WORKDIR /app
ADD target/online-compiler.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]