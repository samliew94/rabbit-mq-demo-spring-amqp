# Build stage
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /home/app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Package stage
FROM eclipse-temurin:17-jdk
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
