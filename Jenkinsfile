pipeline {
    agent any
    tools {
           maven "3.6.0"
    }
    stages {
        stage ('Initialize') {
                    steps {
                        sh '''
                            echo "PATH = ${PATH}"
                            echo "M2_HOME = ${M2_HOME}"
                        '''
                    }
                }
        stage('Build News API') {
            steps {
                sh "mvn clean install"
            }
        }
    }
}