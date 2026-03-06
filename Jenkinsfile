pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = '505342112116'
        AWS_REGION = 'ap-south-1'
        ECR_REPO = 'payment-service'
        IMAGE_TAG = "${BUILD_NUMBER}"

        CLUSTER_NAME = 'payment-service'
        SERVICE_NAME = 'payment-springboot-service'

        ECR_URI = '505342112116.dkr.ecr.ap-south-1.amazonaws.com/payment-service'
    }

    tools {
        maven 'maven'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/madhumallela99/payment-service.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                docker build -t $ECR_REPO:$IMAGE_TAG .
                docker tag $ECR_REPO:$IMAGE_TAG $ECR_URI:$IMAGE_TAG
                '''
            }
        }

        stage('Login to ECR') {
            steps {
                sh '''
                aws ecr get-login-password --region $AWS_REGION \
                | docker login --username AWS \
                --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
                '''
            }
        }

        stage('Push Image to ECR') {
            steps {
                sh '''
                docker push $ECR_URI:$IMAGE_TAG
                '''
            }
        }

        stage('Deploy to ECS (Rolling Update)') {
            steps {
                sh '''
                aws ecs update-service \
                --cluster $CLUSTER_NAME \
                --service $SERVICE_NAME \
                --force-new-deployment \
                --region $AWS_REGION
                '''
            }
        }

    }

    post {
        success {
            echo "Deployment Successful 🚀"
        }
        failure {
            echo "Deployment Failed ❌"
        }
    }
}
