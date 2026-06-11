pipeline {
agent any

tools {
    maven 'Maven-3.9'
}

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
