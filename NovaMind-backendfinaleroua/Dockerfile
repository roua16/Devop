# Étape 1 : construire le projet avec Maven
FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /app

# Copier uniquement les fichiers essentiels pour Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw . 
COPY mvnw.cmd .

# Vérifier que le pom.xml est bien présent
RUN ls -la /app

# Télécharger les dépendances Maven
RUN ./mvnw dependency:go-offline

# Copier seulement le code source
COPY src ./src

# Construire l'application sans tests
RUN ./mvnw clean package -DskipTests

# Étape 2 : image finale avec OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copier le jar construit depuis l'étape build
COPY --from=build /app/target/*.jar app.jar

# Exposer le port 8080
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
