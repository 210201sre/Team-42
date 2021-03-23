pipeline {
  agent {
    kubernetes {
      label 'build-agent'
      defaultContainer 'jnlp'
      yaml """
      apiVersion: v1
      kind: Pod
      metadata:
      labels:
        component: ci
      spec:
        containers:
        - name: jnlp
          image: odavid/jenkins-jnlp-slave:jdk11
          workingDir: /home/jenkins
          env:
          - name: DOCKER_HOST
            value: tcp://localhost:2375
          resources:
            requests:
              memory: "1000Mi"
              cpu: "0.3"
            limits:
              memory: "1500Mi"
              cpu: "1.5"
        - name: dind-daemon
          image: docker:18-dind
          workingDir: /var/lib/docker
          securityContext:
            privileged: true
          volumeMounts:
          - name: docker-storage
            mountPath: /var/lib/docker
          resources:
            requests:
              memory: "1000Mi"
              cpu: "0.3"
            limits:
              memory: "1500Mi"
              cpu: "1.5"
        - name: kubectl
          image: jshimko/kube-tools-aws:latest
          command:
          - cat
          tty: true
        volumes:
        - name: docker-storage
          emptyDir: {}
      """
    }
  }

  environment {
    DOCKER_IMAGE_NAME = 'revteam42/project2'
  }

  stages {
    stage('Build') {
      steps {
        sh 'docker build -t $DOCKER_IMAGE_NAME .'
        script {
          app = docker.image(DOCKER_IMAGE_NAME)
        }
        sh 'docker images'
      }
    }
    
    stage('Sonar Quality Check') {
      steps {
        sh 'java -version'
        sh 'chmod +x mvnw'
        withSonarQubeEnv(credentialsId: 'sonar-token-42', installationName: 'sonarcloud') {
          sh './mvnw -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
        }
      }
    }

    stage('Push Docker Image') {
      steps {
        script {
          docker.withRegistry('https://registry.hub.docker.com', 'team42') {
            app.push('latest')
            app.push("${env.BUILD_NUMBER}")
            app.push("${env.GIT_COMMIT}")
          }
        }
      }
    }


    stage('kuberneties canary deployment') {
      steps{
        script {
          container('kubectl') {
            withKubeConfig([credentialsId: 'kubeconfig']) {
              sh "aws eks update-kubeconfig --name matt-oberlies-sre-943"
              sh 'kubectl get all -n team42'
              sh 'kubectl scale deployment project2-canary --replicas=1 -n team42'
              sh 'sleep 5s'
			        sh 'kubectl get all -n team42'
            }
          }
        }
      }
    }

    stage('kuberneties production deployment') {
      steps{
        script {
          container('kubectl') {
            withKubeConfig([credentialsId: 'kubeconfig']) {
              sh "aws eks update-kubeconfig --name matt-oberlies-sre-943"
              sh 'kubectl get all -n team42'
              input 'Deploy to Production?'
               sh 'kubectl scale deployment project2-canary --replicas=0 -n team42'
              sh 'kubectl set image -n team42 deployment project2 project2=$DOCKER_IMAGE_NAME'
              sh 'sleep 5s'
			        sh 'kubectl get all -n team42'
            }
          }
        }
      }
    }
}
}