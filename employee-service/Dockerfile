# Stage 1: Build with Maven 3 and Java 17
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Run with lightweight JRE 17
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/employee-service-1.0.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
