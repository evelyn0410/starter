pipeline {
    agent any
    environment {
        PATH = "$PATH:/usr/local/bin/"  // skaffold, argocd path
        SOURCECODE_JENKINS_CREDENTIAL_ID = 'evelyn-git'
        SOURCE_CODE_URL = 'https://github.com/evelyn0410/starter.git'
        RELEASE_BRANCH = 'main'
        REGISTRY = "192.168.41.50:5000"
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

        stage('Build') {
            steps {
                script{
                    docker.withRegistry("$REGISTRY","nexus-docker"){   //credential 이름이 jenkins에 등록된 이름과 동일해야 함, jenkins에 docker deploy 권한 필요
                        sh "pwd"
                        sh "chmod u+x ./gradlew"
                        sh "skaffold build -p dev -t ${TAG}"
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