# Étape 1 : construire le projet avec Maven
FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /app

# Copier uniquement le pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source
COPY src ./src

# Construire l’application
RUN mvn clean package -DskipTests

# Étape 2 : image finale avec OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
