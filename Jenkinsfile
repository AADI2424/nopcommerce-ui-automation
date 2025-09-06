pipeline {
  agent any
  tools { jdk 'jdk17'; maven 'maven3.9' }

  options {
    timestamps()
    ansiColor('xterm')
    buildDiscarder(logRotator(numToKeepStr: '20'))
    disableConcurrentBuilds()
  }

  // If you can't set webhooks yet, uncomment the next line to poll every ~2 mins:
  // triggers { pollSCM('H/2 * * * *') }

  environment {
    MAVEN_OPTS = '-Dmaven.test.failure.ignore=false'
    BROWSER    = 'chrome'
    HEADLESS   = 'true'
  }

  stages {
    stage('Checkout'){ steps { checkout scm } }
    stage('Verify Tools'){ steps { bat 'mvn -v' ; bat 'java -version' } }
    stage('Test') {
      steps {
        bat 'mvn -B -e clean test -Dbrowser=%BROWSER% -Dheadless=%HEADLESS%'
      }
      post {
        always {
          publishHTML(target: [
            reportDir: 'target',
            reportFiles: 'cucumber-report.html',
            reportName: 'Cucumber HTML',
            keepAll: true,
            alwaysLinkToLastBuild: true,
            allowMissing: true
          ])
          junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true, keepLongStdio: true
          archiveArtifacts artifacts: 'target/**/*.json, target/**/*.html, target/**/screenshots/**/*.* , target/surefire-reports/**/*.*', fingerprint: true
        }
      }
    }
  }

  post {
    success { echo ' All tests passed.' }
    failure { echo ' Tests failed — check reports.' }
  }
}
