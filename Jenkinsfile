pipeline {
  agent any

  // Match Jenkins Global Tool names exactly
  tools { 
    jdk 'JDK17'
    maven 'M3'
  }

  options {
    timestamps()
    buildDiscarder(logRotator(numToKeepStr: '20'))
  }

  // Choose either webhook trigger or polling (uncomment one)
  triggers {
    // pollSCM('H/2 * * * *')   // polls every ~2 minutes
    // githubPush()            // if you configured a GitHub webhook
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test') {
      steps {
        // Windows agent -> use 'bat'. (On Linux, switch to 'sh')
        bat 'mvn -B -e clean test -Dmaven.test.failure.ignore=false -Dbrowser=chrome -Dheadless=true'
      }
    }
  }

  post {
    always {
      junit 'target/surefire-reports/*.xml'
      publishHTML(target: [
        reportDir: 'target',
        reportFiles: 'cucumber-report.html',
        reportName: 'Cucumber HTML'
      ])
      archiveArtifacts allowEmptyArchive: true, artifacts: 'target/**/*.json, target/**/*.html, target/surefire-reports/*.xml, target/**/screenshots/**/*'
    }
  }
}
