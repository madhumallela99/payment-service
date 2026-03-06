pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = "505342112116"
        AWS_REGION = "ap-south-1"
        ECR_REPO = "payment-service"
        IMAGE_TAG = "${BUILD_NUMBER}"
        ECR_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO}"
        ARTIFACT_BUCKET = "ecs-codedeploy-artifacts"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git 'https://github.com/madhumallela99/payment-service.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh """
                docker build -t $ECR_REPO:$IMAGE_TAG .
                docker tag $ECR_REPO:$IMAGE_TAG $ECR_URI:$IMAGE_TAG
                """
            }
        }

        stage('Login to ECR') {
            steps {
                sh """
                aws ecr get-login-password --region $AWS_REGION | \
                docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
                """
            }
        }

        stage('Push Image to ECR') {
            steps {
                sh """
                docker push $ECR_URI:$IMAGE_TAG
                """
            }
        }

        stage('Update Task Definition') {
            steps {
                sh """
                sed -i 's|IMAGE_URI|$ECR_URI:$IMAGE_TAG|g' taskdef.json
                """
            }
        }

        stage('Upload Artifact to S3') {
            steps {
                sh """
                zip deployment.zip appspec.yaml taskdef.json
                aws s3 cp deployment.zip s3://$ARTIFACT_BUCKET/deployment.zip
                """
            }
        }
    }
}
