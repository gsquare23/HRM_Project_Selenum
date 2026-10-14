
pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven386'
    }

    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(
            numToKeepStr: '20',
            artifactNumToKeepStr: '10'
        ))
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
            script {
                // Publish TestNG/Surefire XML results.
                junit(
                    testResults: 'target/surefire-reports/TEST-*.xml',
                    allowEmptyResults: true
                )

                // Keep test reports, screenshots and test output.
                archiveArtifacts(
                    artifacts: 'target/surefire-reports/**,test-output/**,screenshots/**',
                    allowEmptyArchive: true,
                    fingerprint: true
                )

                // Display the Extent HTML report on the build page.
                publishHTML(target: [
                    reportName: 'Extent Report',
                    reportDir: 'test-output',
                    reportFiles: 'ExtentReport.html',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: true
                ])
            }
        }

        success {
            echo 'Selenium test execution completed successfully.'
        }

        failure {
            echo 'Build failed. Check Console Output, TestNG results and Extent Report.'
        }

        unstable {
            echo 'Build is unstable. Review the test results.'
        }
    }
}
