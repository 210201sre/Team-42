pipeline {
    // Author: Matthew Oberlies
    agent any
    environment {
        // Can declare different environment variables to be used specifically within this pipeline
        DOCKER_IMAGE_NAME = 'ikenoxamos/project1'
        MAVEN_IMAGE_NAME = 'log-aggregation-demo:0.0.1-SNAPSHOT'
    }

    stages {

        stage('Build') {
            steps {
                sh 'chmod +x mvnw && ./mvnw spring-boot:build-image'
                sh 'docker tag $MAVEN_IMAGE_NAME $DOCKER_IMAGE_NAME'
                script {
                    app = docker.image(DOCKER_IMAGE_NAME)
                }
            }
        }

       }

}