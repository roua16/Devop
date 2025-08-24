pipeline {
    agent any

    environment {
        IMAGE_NAME = "roua211jft7404/novamind-backend" // nom de l'image Docker
    }

    stages {
        // Étape 1 : Git Checkout
        stage('Checkout') {
            steps {
                git branch: 'develop',
                    url: 'https://github.com/roua16/Devop.git',
                    credentialsId: 'Git' // Ton GitHub token dans Jenkins
            }
        }

        // Étape 2 : Docker Login sécurisé
        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                }
            }
        }

        // Étape 3 : Docker Build
        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$BUILD_NUMBER .'
                sh 'docker tag $IMAGE_NAME:$BUILD_NUMBER $IMAGE_NAME:latest' // Tag "latest"
            }
        }

        // Étape 4 : Docker Push
        stage('Docker Push') {
            steps {
                sh 'docker push $IMAGE_NAME:$BUILD_NUMBER'
                sh 'docker push $IMAGE_NAME:latest'
            }
        }
    }

    post {
        success {
            echo 'Build et push Docker réussis !'
        }
        failure {
            echo 'Le pipeline a échoué. Vérifie les logs.'
        }
    }
}
