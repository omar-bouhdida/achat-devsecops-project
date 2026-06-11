pipeline {
agent any

stages {

    stage('Checkout') {
        steps {
            checkout scm
        }
    }

    stage('Build') {
        steps {
            sh 'mvn clean package'
        }
    }

    stage('Test') {
        steps {
            sh 'mvn test'
        }
    }
}

post {
    success {
        echo 'Build completed successfully'
    }

    failure {
        echo 'Build failed'
    }
}

}
