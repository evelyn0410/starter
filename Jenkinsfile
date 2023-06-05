pipeline {
    agent any
    environment {
        PATH = "$PATH:/usr/local/bin/"  // skaffold, argocd path
        SOURCECODE_JENKINS_CREDENTIAL_ID = 'evelyn-git'
        SOURCE_CODE_URL = 'https://github.com/evelyn0410/starter.git'
        RELEASE_BRANCH = 'main'
    }

    stages {
        stage('init') {
            steps {
                echo 'init'
                echo "Current workspace : ${workspace}"
            }
        }

       stage('checkout') {
            steps {
                echo 'clone'
                git url: "$SOURCE_CODE_URL",
                branch: "$RELEASE_BRANCH",
                credentialsId: "$SOURCECODE_JENKINS_CREDENTIAL_ID"
                sh "ls -al"
            }
       }

        stage('SonarQube Analysis') {
            steps{
                script {
                    def scannerHome = tool 'SonarScanner';
                     withSonarQubeEnv(credentialsId:"SONAR_TOKEN", installationName:'sonarqube') {
                      sh "./gradlew bootJar"
                      sh "${scannerHome}/bin/sonar-scanner"
                    }
                }
            }
          }

        stage('workspace clear'){
            steps {
                cleanWs()
            }
        }
    }
}