pipeline {
    agent any

    environment {
        APP_NAME = "nova-backend"
        DOCKER_IMAGE = "roua211jft7404/${APP_NAME}:latest"
    }

    stages {
        // -----------------------
        // 1️⃣ Nettoyage du workspace
        // -----------------------
        stage('Clean Workspace') {
            steps {
                echo "🧹 Nettoyage du workspace..."
                deleteDir()
            }
        }

        // -----------------------
        // 2️⃣ Checkout du code depuis Git
        // -----------------------
        stage('Checkout SCM') {
            steps {
                echo "📥 Checkout du code..."
                checkout scm
            }
        }

        // -----------------------
        // 3️⃣ Build Maven
        // -----------------------
        stage('Build Maven') {
            steps {
                echo "🔨 Compilation Maven..."
                sh 'mvn -f pom.xml clean package -DskipTests'
            }
        }

        // -----------------------
        // 4️⃣ Build Docker Image
        // -----------------------
        stage('Build Docker Image') {
            steps {
                echo "🐳 Build de l’image Docker..."
                sh "docker build -t $DOCKER_IMAGE ."
            }
        }

        // -----------------------
        // 5️⃣ Push Docker Image sur DockerHub
        // -----------------------
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

        // -----------------------
        // 6️⃣ Lancer Monitoring Stack
        // Prometheus + Grafana + Alertmanager
        // -----------------------
        stage('Start Monitoring Stack') {
            steps {
                echo "📊 Lancement de Prometheus, Grafana et Alertmanager..."
                sh 'docker-compose -f docker-compose-monitoring.yml up -d'
            }
        }

        // -----------------------
        // 7️⃣ Tests Unitaires et d’Intégration
        // -----------------------
        stage('Unit & Integration Tests') {
            steps {
                echo "🧪 Lancement des tests unitaires et d’intégration..."
                sh 'mvn test'
            }
        }

        // -----------------------
        // 8️⃣ Load Tests
        // -----------------------
        stage('Load Tests') {
            steps {
                echo "⚡ Lancement des tests de charge..."
                sh 'docker run --rm -v $(pwd)/load-tests:/load-tests loadimpact/k6 run /load-tests/script.js'
            }
        }
    }

    // -----------------------
    // Post Actions
    // -----------------------
    post {
        success {
            echo "✅ Pipeline terminé avec succès !"
        }
        failure {
            echo "❌ Pipeline échoué. Vérifier les logs."
        }
    }
}
