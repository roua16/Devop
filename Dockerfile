# Étape 1 : construire le projet avec Maven + JDK 21
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copier le pom.xml seul pour télécharger les dépendances offline
COPY NovaMind-backendfinaleroua/pom.xml ./
RUN mvn dependency:go-offline

# Copier le code source
COPY NovaMind-backendfinaleroua/src ./src

# Compiler le projet (skip tests)
RUN mvn clean package -DskipTests

# Étape 2 : créer l'image finale avec juste le jar
FROM eclipse-temurin:21-jdk-jammy AS runtime
WORKDIR /app

# Copier le jar depuis l'étape build
COPY --from=build /app/target/*.jar app.jar

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
# Lancer l'application
