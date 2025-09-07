pipeline {
    agent any

    environment {
        APP_NAME = "nova-backend"
        DOCKER_IMAGE = "roua211jft7404/${APP_NAME}:latest"
        SONARQUBE_TOKEN = credentials('sonarqube-token') // Token SonarQube stocké dans Jenkins
    }

    stages {
        stage('Clean Workspace') {
            steps {
                echo "🧹 Nettoyage du workspace..."
                deleteDir()
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

        stage('Static Analysis - SonarQube') {
            steps {
                echo "🔍 Analyse statique avec SonarQube..."
                sh """
                sonar-scanner \
                  -Dsonar.projectKey=${APP_NAME} \
                  -Dsonar.sources=. \
                  -Dsonar.host.url=http://localhost:9000 \
                  -Dsonar.login=${SONARQUBE_TOKEN}
                """
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Build de l’image Docker..."
                sh "docker build -t $DOCKER_IMAGE ."
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "⬆️ Push de l’image Docker sur DockerHub..."
                withCredentials([usernamePassword(credentialsId: 'dockerhub', 
                                                 usernameVariable: 'DOCKERHUB_USER', 
                                                 passwordVariable: 'DOCKERHUB_PASS')]) {
                    sh """
                    echo "$DOCKERHUB_PASS" | docker login -u "$DOCKERHUB_USER" --password-stdin
                    docker push $DOCKER_IMAGE
                    """
                }
            }
        }

        stage('Start Monitoring Stack') {
            steps {
                echo "📊 Lancement de Prometheus, Grafana et Alertmanager via Docker Compose..."
                sh 'docker-compose -f docker-compose.yml up -d'
            }
        }

        stage('Unit & Integration Tests') {
            steps {
                echo "🧪 Lancement des tests unitaires et d’intégration..."
                sh 'mvn test' // ou pytest selon ton projet
            }
        }

        stage('Load Tests') {
            steps {
                echo "⚡ Lancement des tests de charge..."
                sh 'docker run --rm -v $(pwd)/load-tests:/load-tests loadimpact/k6 run /load-tests/script.js'
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

