# Étape 1 : build Maven
FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /app

# Copier le pom.xml pour télécharger les dépendances
COPY NovaMind-backendfinaleroua/pom.xml ./ 
RUN mvn dependency:go-offline

# Copier le code source
COPY NovaMind-backendfinaleroua/src ./src

# Construire le projet
RUN mvn clean package -DskipTests

# Étape 2 : image finale
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copier le jar depuis l'étape build
COPY --from=build /app/target/*.jar app.jar

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
