pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('DockerHub')
        IMAGE_NAME = "roua211jft7404/novamind-backend"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop',
                    url: 'https://github.com/roua16/Devop.git',
                    credentialsId: 'Git'
            }
        }

        stage('Docker Login') {
            steps {
                sh """
                echo "${DOCKERHUB_CREDENTIALS_PSW}" | docker login -u "${DOCKERHUB_CREDENTIALS_USR}" --password-stdin
                """
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$BUILD_NUMBER .'
            }
        }

        stage('Docker Push') {
            steps {
                sh 'docker push $IMAGE_NAME:$BUILD_NUMBER'
            }
        }
    }
}
