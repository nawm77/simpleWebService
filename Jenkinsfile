pipeline {
    agent { label 'agent' }

    environment {
        DOCKER_IMAGE = 'web-app'
        DOCKER_REGISTRY = 'nawm77'
        DOCKER_TAG = "${BUILD_ID}"
        COMPOSE_FILE = 'docker-compose.yaml'
    }

    parameters {
        gitParameter (  branch: '',
                        branchFilter: 'origin/(.*)',
                        defaultValue: 'main',
                        description: '',
                        name: 'BRANCH',
                        quickFilterEnabled: true,
                        selectedValue: 'TOP',
                        sortMode: 'DESCENDING',
                        tagFilter: '*',
                        type: 'PT_BRANCH',
                        useRepository: 'git@github.com:nawm77/simpleWebService.git')
        booleanParam(name: 'DEPLOY', defaultValue: false, description: 'Запуск с деплоем (true/false)')
    }

    stages {
        stage('Delete workspace before build starts') {
            steps {
                echo 'Deleting workspace'
                deleteDir()
            }
        }
        stage('Env print') {
            steps {
                sh '''
                    echo $BRANCH_NAME
                '''
            }
        }

        stage('Checkout') {
            steps{
                    git branch: "${params.BRANCH}", credentialsId: 'github', url: 'git@github.com:nawm77/simpleWebService.git'
                }
        }

        stage('Docker Login') {
            steps {
                script {
                    echo "Logging in to DockerHub..."
                    withCredentials([usernamePassword(credentialsId: 'docker_hub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh """
                        echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                        """
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker image..."
                    sh "docker build -t ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    echo "Pushing Docker image to registry..."
                    sh "docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
            }
        }

        stage('Deploy (Optional)') {
            when {
                expression { params.DEPLOY == true }
            }
            steps {
                script {
                    echo "Deploying Docker container..."
                    sh "docker-compose up -d"
                }
            }
        }
        stage('Show active containers') {
          steps {
            script{
              sh "docker ps"
            }
          }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed!"
        }
    }
}