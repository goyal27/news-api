pipeline {
    agent any
    stages {
        stage('Build News API') {
            steps {
                sh "mvn clean install"
            }
        }
    }
}