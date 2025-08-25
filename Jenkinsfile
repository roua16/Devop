pipeline {
    agent any
    environment {
        IMAGE_NAME = "monrepo/novamind-backend:latest"
    }
    stages {
        stage('Build Maven') {
            steps {
                echo "🔨 Compilation Maven..."
                // Chemin vers le pom.xml
                sh 'mvn -f NovaMind-backendfinaleroua/pom.xml clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                echo "🐳 Build Docker Image..."
                // Chemin vers le dossier contenant le Dockerfile
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }
        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    sh "docker push ${IMAGE_NAME}"
                }
            }
        }
    }
}
