pipeline {
    agent {
    label 'your-node-label'
     } // Replace with the appropriate Jenkins node label
    environment {
        APP_PORT = '8000' // Application port
        JAVA_HOME = '/path/to/java21' // Path to Java 21
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
        GIT_REPO_URL = 'https://your-repo-url.git' // Replace with your Git repository URL
        GIT_BRANCH = 'main' // Replace with your branch name
        DEPLOY_DIR = '/opt/spring-boot-app' // Directory to copy and run the JAR file
    }
    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning the Git repository...'
                sh """
                    if [ -d "workspace" ]; then
                        rm -rf workspace
                    fi
                    mkdir workspace
                    cd workspace
                    git clone --branch ${GIT_BRANCH} ${GIT_REPO_URL} .
                """
            }
        }
        stage('Build Application') {
            steps {
                echo 'Building the Spring Boot application...'
                dir('workspace') {
                    script {
                        // Use Gradle or Maven to build the application
                        if (fileExists('gradlew')) {
                            sh './gradlew clean build'
                        } else if (fileExists('pom.xml')) {
                            sh 'mvn clean package'
                        } else {
                            error 'No build file found (Gradle or Maven)!'
                        }
                    }
                }
            }
        }
        stage('Copy and Deploy') {
            steps {
                echo 'Copying JAR file to deploy directory and running the application...'
                dir('workspace') {
                    script {
                        // Identify the JAR file
                        def jarFile = ''
                        if (fileExists('build/libs')) {
                            jarFile = sh(script: "ls build/libs/*.jar | head -n 1", returnStdout: true).trim()
                        } else if (fileExists('target')) {
                            jarFile = sh(script: "ls target/*.jar | head -n 1", returnStdout: true).trim()
                        } else {
                            error 'Built JAR file not found!'
                        }

                        // Ensure the deploy directory exists
                        sh """
                            mkdir -p ${DEPLOY_DIR}
                            cp ${jarFile} ${DEPLOY_DIR}/app.jar
                        """

                        // Kill any running application on the same port
                        sh """
                            if [ -f "${DEPLOY_DIR}/app.pid" ]; then
                                kill \$(cat ${DEPLOY_DIR}/app.pid) || true
                                rm -f ${DEPLOY_DIR}/app.pid
                            fi
                        """

                        // Run the application
                        sh """
                            nohup java -jar ${DEPLOY_DIR}/app.jar --server.port=${APP_PORT} > ${DEPLOY_DIR}/app.log 2>&1 &
                            echo \$! > ${DEPLOY_DIR}/app.pid
                        """
                    }
                }
            }
        }
        stage('Verify Deployment') {
            steps {
                echo 'Verifying the application deployment...'
                script {
                    sh "curl -s http://localhost:${APP_PORT} || echo 'Application not reachable!'"
                }
            }
        }
    }
    post {
        always {
            echo 'Cleaning up temporary files...'
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
