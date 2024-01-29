pipeline {
    agent any
    environment {
        PATH = "$PATH:/usr/local/bin/"  // skaffold path
        SOURCECODE_JENKINS_CREDENTIAL_ID = 'evelyn-git'
        SOURCE_CODE_URL = 'https://github.com/evelyn0410/starter.git'
        RELEASE_BRANCH = 'main'
        GITOPS_URL = "https://github.com/oscka/gitops-openmsa"
        GITOPS_BRANGH = "main"
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
                    docker.withRegistry("nexus-docker-2"){
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

        stage('GitOps update') {
            steps {
                    print "====== kustomization.yaml tag update ====="
                    withCredentials([
                        gitUsernamePassword(credentialsId: 'binam33-git', gitToolName: 'Default')
                    ]) {
                        sh """
                        git clone ${GITOPS_URL}
                        cd ./gitops-openmsa/${PROJECT_NAME}/rolling-update
                        kustomize edit set image jei0486/${PROJECT_NAME}:${TAG}
                        git config --global user.email "gitops@osckorea.com"
                        git config --global user.name "gitops"
                        git add .
                        git commit -am 'update image tag ${TAG}'
                        git remote set-url --push origin ${GITOPS_URL}
                        git push origin ${GITOPS_BRANGH}
                        """
                    }
                    print "git push finished !!!"
                }                    
        }
    }
}
