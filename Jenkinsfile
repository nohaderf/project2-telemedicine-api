pipeline {
    environment {
        registry = 'nohaderf/project2'
        dockerHubCreds = 'docker_hub'
        dockerImage = ''
    }
    agent any
    stages {
        stage('Quality Gate') {
            steps{
                echo 'Quality Gate'
            }
        }
        stage('Unit Testing') {
            when {
                anyOf {branch 'ft_*'; branch 'bg_*'}
            }
            steps {
                sh 'mvn test'
            }
        }
        stage('Build') {
            when {
                branch 'master'
            }
            steps{
                sh 'mvn package -DskipTests'
            } 
        }
        stage('Docker Image') {
            when {
                branch 'master'
            }
            steps {
                script {
                    echo "$registry:$currentBuild.number"
                    dockerImage = docker.build "$registry"
                }
            }
        }
        stage('Docker Deliver') {
            when {
                branch 'master'
            }
            steps {
                script {
                    docker.withRegistry("", dockerHubCreds) {
                        dockerImage.push("$currentBuild.number")
                        dockerImage.push("latest")
                    }
                }
            }
        }
        stage('Wait for approval') {
            steps {
                echo 'Wait for approval'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploy'
            }
        }
    }
}