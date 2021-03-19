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
              memory: "900"
              cpu: "0.3"
            limits:
              memory: "999Mi"
              cpu: "0.5"
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
              memory: "900Mi"
              cpu: "0.3"
            limits:
              memory: "999Mi"
              cpu: "0.5"
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

    stage('kuberneties') {
      steps{
        script {
          container('kubectl') {
            withKubeConfig([credentialsId: 'kubeconfig']) {
              sh "aws eks update-kubeconfig --name matt-oberlies-sre-943"
              sh 'kubectl get all -n team42'
			  sh 'kubectl apply project2-deployment.yml'
			  sh 'kubectl get all -n team42'
            }
          }
        }
      }
    }
}
}