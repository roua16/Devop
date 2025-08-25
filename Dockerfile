# Étape 1 : build Maven
FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : image finale
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
