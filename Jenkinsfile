pipeline {
    agent { label 'docker-agent' }
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
        timeout(time: 1, unit: 'HOURS')
    }
    
    environment {
        MAVEN_HOME = '/usr/share/maven'
        JAVA_HOME = '/opt/java/openjdk'
        PATH = "${MAVEN_HOME}/bin:${JAVA_HOME}/bin:${PATH}"
    }
    
    stages {
        stage('Clean and Verify') {
            agent {
                docker { 
                    image 'maven:3.9.15-eclipse-temurin-25'
                    args '-v maven-repo:/tmp/workspace/maven-cache'
                    reuseNode true 
                }
            }
            environment {
                MAVEN_OPTS = '-Dmaven.repo.local=/tmp/workspace/maven-cache'
            }
            steps {
                sh 'mvn clean verify -s settings.xml'
            }
            
        }
         stage('Compile and install') {
            agent {
                docker { 
                    image 'maven:3.9.15-eclipse-temurin-25'
                    args '-v maven-repo:/tmp/workspace/maven-cache'
                    reuseNode true 
                }
            }
            environment {
                MAVEN_OPTS = '-Dmaven.repo.local=/tmp/workspace/maven-cache'
            }
            steps {
                sh 'mvn install -s settings.xml'
            }
            
        }

        stage('Deploy Artifact') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'main'
                }
            }
            agent {
                docker { 
                    image 'maven:3.9.15-eclipse-temurin-25'
                    args '-v maven-repo:/tmp/workspace/maven-cache'
                    reuseNode true 
                }
            }
            environment {
                MAVEN_OPTS = '-Dmaven.repo.local=/tmp/workspace/maven-cache'
            }
            steps {
                withCredentials([usernamePassword(credentialsId: 'jenkins2nexus-deployement', usernameVariable: 'MAVEN_USER', passwordVariable: 'MAVEN_PWD')]) {
                    sh 'mvn deploy -Djenkins-username=$MAVEN_USER -Djenkins-pwd=$MAVEN_PWD -s settings.xml' 
                }
            }
        }
        stage('Build DOCKER images and store in registry') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'main'
                }
            }
            agent {
                docker { 
                    image 'maven:3.9.6-eclipse-temurin-21'
                    args '-v maven-repo:/tmp/workspace/maven-cache'
                    reuseNode true 
                }
            }
            environment {
                MAVEN_OPTS = '-Dmaven.repo.local=/tmp/workspace/maven-cache'
            }
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-registry-credentials', usernameVariable: 'REGISTRY_USER', passwordVariable: 'REGISTRY_PWD')]) {
                    sh 'mvn deploy -registry.username=$REGISTRY_USER -Dregistry.password=$REGISTRY_PWD -P DOCKER -P PUSH -s settings.xml'
                }
                
            }
        }
        stage('Build Site') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'main'
                }
            }
            agent {
                docker { 
                    image 'maven:3.9.15-eclipse-temurin-25'
                    args '-v maven-repo:/tmp/workspace/maven-cache'
                    reuseNode true 
                }
            }
            environment {
                MAVEN_OPTS = '-Dmaven.repo.local=/tmp/workspace/maven-cache'
            }
            steps {
                sh 'mvn site:site -s settings.xml'
            }
        }
        stage('Staging Site') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'main'
                }
            }
            agent {
                docker { 
                    image 'maven:3.9.15-eclipse-temurin-25'
                    args '-v maven-repo:/tmp/workspace/maven-cache'
                    reuseNode true 
                }
            }
            environment {
                MAVEN_OPTS = '-Dmaven.repo.local=/tmp/workspace/maven-cache'
            }
            steps {
                sh 'mvn site:stage -s settings.xml'
            }
        }
        stage('Deploy Site') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'main'
                }
            }
            agent {
                docker { 
                    image 'alpine:latest'
                    reuseNode true 
                }
            }
            environment {
                SERVER_IP = credentials('hostname_server')  // Référence le credential Jenkins
            }
            steps {
                script {
                    echo "📤 Déploiement du site Maven via SCP vers https://collonvillethomas.freeboxos.fr/public/projets/"
                    
                    // Déployer le site généré via SCP avec clé SSH
                    withCredentials([sshUserPrivateKey(credentialsId: 'home-ssh-key', keyFileVariable: 'SSH_KEY', usernameVariable: 'SITE_USER')]) {
                        sh '''
                            # Installer openssh-client dans Alpine
                            apk add --no-cache openssh-client
                            
                            # Créer un répertoire temporaire pour la clé SSH
                            mkdir -p ~/.ssh
                            cp $SSH_KEY ~/.ssh/id_rsa
                            chmod 600 ~/.ssh/id_rsa
                            
                            # Ajouter le serveur aux hosts connus (éviter la confirmation)
                            ssh-keyscan -H ${SERVER_IP} >> ~/.ssh/known_hosts 2>/dev/null || true
                            pwd
                            cd  ./target/staging
                            scp -r ./ ${SITE_USER}@${SERVER_IP}:/mnt/nfs_storage_client/docker_share/tc-public-share/html/projets/${BRANCH_NAME}/equinox-loader

                            # Nettoyer la clé temporaire
                            rm -f ~/.ssh/id_rsa
                            
                            echo "✅ Site déployé avec succès sur https://collonvillethomas.freeboxos.fr/public/projets/"
                        '''
                    }
                }
            }
        }
 
        
    }
    
    post {
       
        success {
            echo "✅ Build réussi pour la branche ${BRANCH_NAME}"
            script {
                echo "Nettoyage des ressources..."
            }
            cleanWs()
        }
        
        failure {
            echo "❌ Build échoué pour la branche ${BRANCH_NAME}"
        }
        
        unstable {
            echo "⚠️ Build instable pour la branche ${BRANCH_NAME}"
        }
    }
}
