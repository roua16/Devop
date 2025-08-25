pipeline {
    agent any
    environment {
        IMAGE_NAME = "monrepo/novamind-backend:latest"
    }
    stages {
        stage('Clean Workspace') {
            steps {
                echo "🧹 Nettoyage du workspace..."
                deleteDir()
            }
        }
        stage('Build Maven') {
            steps {
                echo "🔨 Compilation Maven..."
                sh 'mvn -f NovaMind-backendfinaleroua/pom.xml clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                echo "🐳 Construction de l’image Docker..."
                sh "docker build -t ${IMAGE_NAME} NovaMind-backendfinaleroua/"
            }
        }
        stage('Push Docker Image') {
            steps {
                echo "📤 Push de l’image Docker sur DockerHub..."
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin"
                    sh "docker push ${IMAGE_NAME}"
                }
            }
        }
    }
    post {
        success {
            echo "✅ Pipeline terminé avec succès !"
        }
        failure {
            echo "❌ Pipeline échoué. Vérifier les logs."
        }
    }
}
