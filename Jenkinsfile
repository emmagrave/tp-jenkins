pipeline {
    agent any

    /* ===== 5. Paramètres ===== */
    parameters {
        string(name: 'JAVA_CMD', defaultValue: 'javac', description: 'Commande Java à utiliser')
        choice(name: 'ENV', choices: ['dev', 'test', 'prod'], description: 'Environnement de déploiement')
    }

    /* ===== 7. Nettoyage du workspace ===== */
    options {
        skipDefaultCheckout(true)
    }

    stages {

        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        /* ===== 1. Clone du dépôt Git ===== */
        stage('Clone Git Repository') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/emmagrave/tp-jenkins'
                    // credentialsId: 'github-creds'  // à décommenter si repo privé
            }
        }

        /* ===== 2. Préparer JUnit et compiler Java ===== */
        stage('Prepare JUnit & Compile') {
            steps {
                sh '''
                javac -cp junit-platform-console-standalone-1.9.3.jar *.java
                '''
            }
        }

        /* ===== 3. Exécution des tests unitaires ===== */
        stage('Run JUnit Tests') {
            when {
                expression { params.ENV == 'dev' || params.ENV == 'test' }
            }
            steps {
                sh '''
                java -jar junit-platform-console-standalone-1.9.3.jar \
                --class-path . \
                --scan-class-path
                '''
            }
        }

        /* ===== 4. Génération du JAR ===== */
        stage('Generate JAR') {
            steps {
                sh '''
                jar cfe FactorialApp.jar Factorial *.class
                '''
            }
        }

        /* ===== 6. Déploiement conditionnel selon l'environnement ===== */
        stage('Deploy DEV') {
            when {
                expression { params.ENV == 'dev' }
            }
            steps {
                echo "Déploiement en environnement DEV"
            }
        }

        stage('Deploy TEST') {
            when {
                expression { params.ENV == 'test' }
            }
            steps {
                echo "Déploiement en environnement TEST"
            }
        }

        stage('Deploy PROD') {
            when {
                allOf {
                    branch 'main'
                    expression { params.ENV == 'prod' }
                }
            }
            steps {
                echo "🚀 Déploiement en PROD"
            }
        }
    }

    /* ===== Post-build Actions ===== */
    post {
        success {
            archiveArtifacts artifacts: 'FactorialApp.jar', fingerprint: true
            echo '✅ Build terminé avec succès'
        }
        failure {
            echo '❌ Build échoué'
        }
    }
}
