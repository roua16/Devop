pipeline {
    agent any

    environment {
        APP_NAME = "nova-backend"
        DOCKER_IMAGE = "roua211jft7404/${APP_NAME}:latest"
    }

    stages {
        stage('Clean Workspace') {
            steps {
                echo "🧹 Nettoyage du workspace..."
                deleteDir()  // nettoyer avant checkout
            }
        }

        stage('Checkout SCM') {
            steps {
                echo "📥 Checkout du code..."
                checkout scm
            }
        }

        stage('Build Maven') {
            steps {
                echo "🔨 Compilation Maven..."
                sh 'mvn -f pom.xml clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Build de l’image Docker..."
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "⬆️ Push de l’image Docker sur DockerHub..."
                // Ici on utilise Username + Password
                withCredentials([usernamePassword(credentialsId: 'dockerhub', 
                                                 usernameVariable: 'DOCKERHUB_USER', 
                                                 passwordVariable: 'DOCKERHUB_PASS')]) {
                    sh '''
                        echo "Connexion à DockerHub..."
                        echo $DOCKERHUB_PASS | docker login -u $DOCKERHUB_USER --password-stdin
                        docker push $DOCKER_IMAGE
                    '''
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
