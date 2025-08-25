pipeline {
    agent any
    environment {
        IMAGE_NAME = "monrepo/novamind-backend:latest"
    }
    stages {
        stage('Build Maven') {
            steps {
                // Maven build en ciblant le pom.xml dans NovaMind-backendfinaleroua
                sh 'mvn -f NovaMind-backendfinaleroua/pom.xml clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                // Docker build en pointant sur le dossier qui contient le Dockerfile
                sh "docker build -t ${IMAGE_NAME} NovaMind-backendfinaleroua/"
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
