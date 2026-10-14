pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Environment Check') {
            steps {
                bat 'java -version'
                bat 'mvn -version'
                bat 'git --version'
            }
        }

        stage('Run Selenium Tests') {
            steps {
                bat 'mvn -B clean test'
            }
        }
    }

    post {
        always {
            junit(
                testResults: 'target/surefire-reports/TEST-*.xml',
                allowEmptyResults: true
            )

            archiveArtifacts(
                artifacts: 'target/surefire-reports/**,test-output/**',
                allowEmptyArchive: true,
                fingerprint: true
            )
        }

        success {
            echo 'Selenium tests passed.'
        }

        failure {
            echo 'Build failed. Check Console Output and test reports.'
        }
    }
}