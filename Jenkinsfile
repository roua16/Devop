pipeline {
    agent any

    environment {
        // Définir les variables si besoin
        APP_NAME = "nova-backend"
        DOCKER_IMAGE = "monrepo/${APP_NAME}:latest"
    }

    stages {
        stage('Checkout SCM') {
            steps {
                echo "📥 Checkout du code..."
                checkout scm
            }
        }

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
                echo "🐳 Build de l’image Docker..."
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "⬆️ Push de l’image Docker sur DockerHub..."
                withCredentials([string(credentialsId: 'dockerhub-password', variable: 'DOCKERHUB_PASS')]) {
                    sh '''
                        echo $DOCKERHUB_PASS | docker login -u monuser --password-stdin
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
