# Étape 1 : construire le projet avec Maven
FROM maven:3.9.9-amazoncorretto-17 AS build

# Répertoire de travail dans le conteneur
WORKDIR /app

# Copier uniquement le dossier contenant le pom.xml
COPY NovaMind-backendfinaleroua/NovaMind-backendfinaleroua/NovaMind-backendfinaleroua/ .

# Télécharger les dépendances Maven pour être offline
RUN mvn dependency:go-offline

# Construire le projet
RUN mvn package

# Étape 2 : créer l'image finale avec juste le jar
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Définir la commande de lancement
ENTRYPOINT ["java","-jar","app.jar"]
