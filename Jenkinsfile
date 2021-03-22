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
    stage('kuberneties') {
      steps{
        script {
          container('kubectl') {
            withKubeConfig([credentialsId: 'kubeconfig']) {
              sh "aws eks update-kubeconfig --name matt-oberlies-sre-943"
              sh 'kubectl get pods -n team42'
              sh 'kubectl get deployments -n team42'
			 // sh 'kubectl patch deployment project2 -n team42 -p "{"spec":{"template":{"spec":{"containers":[{"name":"project2","image":"$DOCKER_IMAGE_NAME"}]}}}}"'
              sh 'kubectl set image -n team42 deployment project2 project2=revteam42/project2'
              sh 'kubectl get secret grafana -o jsonpath="{ .data.admin-password }" | base64 --decode'
			        sh 'kubectl get pods -n team42'
            }
          }
        }
      }
    }
}
}