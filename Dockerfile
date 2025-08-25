# Étape 1 : construire le projet avec Maven
FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /app

# Copier le pom.xml seul pour le téléchargement offline
COPY NovaMind-backendfinaleroua/pom.xml ./
RUN mvn dependency:go-offline

# Copier le code source
COPY NovaMind-backendfinaleroua/src ./src

# Construire le projet
RUN mvn package -DskipTests

# Étape 2 : créer l'image finale avec juste le jar
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copier le jar depuis l'étape build
COPY --from=build /app/target/*.jar app.jar

# Lancer l'application
ENTRYPOINT ["java","-jar","app.jar"]
