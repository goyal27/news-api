pipeline {
    agent any
    tools {
           maven "3.6.0" // You need to add a maven with name "3.6.0" in the Global Tools Configuration page
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