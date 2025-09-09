pipeline {
  agent { label 'windows' }   // make sure this runs on your Windows node

  tools {
    jdk 'JDK17'
    maven 'M3'
  }

  // Auto-build: poll the repo every ~2 minutes (or switch to githubPush() if you add a webhook)
  triggers { pollSCM('H/2 * * * *') }

  options {
    timestamps()
    buildDiscarder(logRotator(numToKeepStr: '20'))
  }

  stages {
    stage('Preflight') {
      steps {
        // Prove the environment has Chrome/Java/Maven and show the Jenkins run user
        bat '''
          echo === WHOAMI & whoami
          echo === USERPROFILE & echo %USERPROFILE%
          echo === JAVA & java -version
          echo === MAVEN & mvn -v
          echo === CHROME VERSION
          "%ProgramFiles%\\Google\\Chrome\\Application\\chrome.exe" --version ^
            || "%ProgramFiles(x86)%\\Google\\Chrome\\Application\\chrome.exe" --version ^
            || (echo Chrome NOT found. Install Chrome system-wide on this agent. & exit /b 2)
        '''
      }
    }

    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build & Test') {
      steps {
        // Resolve Chrome binary path and run tests headless; retry once if driver startup stalls
        retry(2) {
          bat '''
            set CHROME_BIN=%ProgramFiles%\\Google\\Chrome\\Application\\chrome.exe
            if not exist "%CHROME_BIN%" set CHROME_BIN=%ProgramFiles(x86)%\\Google\\Chrome\\Application\\chrome.exe
            echo Using CHROME_BIN=%CHROME_BIN%
            mvn -B -U clean test -Dbrowser=chrome -Dheadless=true -Dchrome.binary="%CHROME_BIN%"
          '''
        }
      }
    }

    stage('Publish Reports') {
      steps {
        junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        publishHTML(target: [
          reportDir: 'target',
          reportFiles: 'cucumber-report.html',
          reportName: 'Cucumber HTML',
          keepAll: true,
          alwaysLinkToLastBuild: true,
          allowMissing: true
        ])
        archiveArtifacts allowEmptyArchive: true,
          artifacts: 'target/**/*.json, target/**/*.html, target/**/screenshots/**/*'
      }
    }
  }

  post {
    always {
      cleanWs deleteDirs: false, notFailBuild: true
    }
  }
}
