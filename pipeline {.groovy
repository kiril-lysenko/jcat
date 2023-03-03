pipeline {
    agent { label 'Built-In Node' }

    stages {
        stage('Docker version') {
            steps {
                sh "echo $USER"
                sh 'docker version'
            }
        }
        stage('Delete workspace before build starts') {
            steps {
                echo 'Deleting workspace'
                deleteDir()
            }
        }
        stage('Checkout') {
            steps{
                git branch: 'main', credentialsId: 'jenkins (git)',
                    url: 'git@github.com:kiril-lysenko/CatInfo.git'        
                }
        }
        stage('Test') {
            steps{
                dir('CatInfo') {
                    sh "ls -la "
                    sh "pwd"
                }
                    sh "ls -la "
                    sh "pwd"
            }
        }
        stage('maven run') {
            steps{
                dir('CatInfo') {
                    sh "mvn install clear"
                }
            }
        }
        stage('Build docker image') {
            steps{
                dir('CatInfo') {ssh
                    sh 'docker-compouse up'
                }
            }
        }
        
        stage('Delete docker image locally') {
            steps{
                sh 'docker rmi -aG$(USER -a)'
            }
        }
    }
}