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

    stage('SonarQube Analysis') {
        steps {
            sh '''
            mvn sonar:sonar \
            -Dsonar.projectKey=achat \
            -Dsonar.host.url=http://sonarqube:9000 \
            -Dsonar.login=sqa_c4d29bd6feeddc1906ee095c274db5a4143ab5da
            '''
        }
    }

    stage('Dependency Check') {
        steps {
            sh '''
            mvn org.owasp:dependency-check-maven:check \
            -DossindexAnalyzerEnabled=false
            '''
        }
    }

    stage('Build Docker Image') {
        steps {
            sh '''
            docker build -t achat-app:${BUILD_NUMBER} .
            '''
        }
    }

    stage('Publish Artifact') {
        steps {
            sh 'mvn deploy'
        }
    }
}

post {
    always {
        archiveArtifacts artifacts: 'target/dependency-check-report.html', allowEmptyArchive: true
    }
}


}

