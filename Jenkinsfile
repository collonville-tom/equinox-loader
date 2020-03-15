pipeline {
    agent { docker { image 'maven:3.6.1-jdk-11-slim' } }
    stages {
        stage('version') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}