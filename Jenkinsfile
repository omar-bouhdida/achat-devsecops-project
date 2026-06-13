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

    stage('OWASP Dependency Check') {
        steps {
            sh '''
            mvn dependency-check:check \
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

    stage('Trivy Scan') {
        steps {
            sh '''
            docker run --rm \
            -v /var/run/docker.sock:/var/run/docker.sock \
            aquasec/trivy image \
            --scanners vuln \
            --format table \
            achat-app:${BUILD_NUMBER} \
            > trivy-report.txt
            '''
        }
    }

    stage('OWASP ZAP Scan') {
        steps {
            sh '''
            docker run --rm \
            --network devops-net \
            -v $WORKSPACE:/zap/wrk \
            ghcr.io/zaproxy/zaproxy:stable \
            zap-baseline.py \
            -t http://achat-app:8089/SpringMVC/ \
            -r zap-report.html
            '''
        }
    }

    stage('Publish Artifact to Nexus') {
        steps {
            sh 'mvn deploy'
        }
    }
}

post {

    always {

        archiveArtifacts(
            artifacts: 'target/dependency-check-report.html',
            allowEmptyArchive: true
        )

        archiveArtifacts(
            artifacts: 'trivy-report.txt',
            allowEmptyArchive: true
        )

        archiveArtifacts(
            artifacts: 'zap-report.html',
            allowEmptyArchive: true
        )
    }

    success {
        echo 'Pipeline completed successfully.'
    }

    failure {
        echo 'Pipeline failed.'
    }
}


}

