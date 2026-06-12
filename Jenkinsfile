pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    triggers {
        pollSCM('* * * * *')
    }

    environment {
        SONAR_TOKEN     = credentials('sonar-token')
        CORREO_DESTINO  = 'luisalds.99@gmail.com'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/LuisaCardenas04/trivia-game-cicd.git'
            }
        }

        stage('Compilar') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Pruebas Unitarias') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Analisis SonarQube') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    bat "mvn sonar:sonar -Dsonar.token=%SONAR_TOKEN%"
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    def qg = waitForQualityGate abortPipeline: false
                    if (qg.status != 'OK') {
                        emailext(
                            subject: "FALLO Quality Gate: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                            body: """
                                El análisis de SonarQube ha fallado.
                                Proyecto   : ${env.JOB_NAME}
                                Build      : #${env.BUILD_NUMBER}
                                URL        : ${env.BUILD_URL}
                                SonarQube  : http://localhost:9000/dashboard?id=trivia-game
                            """,
                            to: "${CORREO_DESTINO}"
                        )
                        error("Quality Gate fallido: ${qg.status}")
                    }
                }
            }
        }

        stage('Empaquetar') {
            steps {
                bat 'mvn package -DskipTests'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        failure {
            emailext(
                subject: "FALLO pipeline: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                body: """
                    El pipeline ha fallado.
                    URL: ${env.BUILD_URL}
                """,
                to: "${CORREO_DESTINO}"
            )
        }
        success {
            echo "Pipeline exitoso. Artefacto disponible en: ${env.BUILD_URL}artifact/"
        }
    }
}