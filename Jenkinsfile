pipeline {
    agent any
    tools {
        maven 'maven-3.6.3'
    }

    triggers {
        githubPush()
    }

    parameters {
        string(name: 'GIT_URL', defaultValue: 'https://github.com/Fenice7/QA_Otus_Homework.git', description: 'The target git url')
        string(name: 'GIT_BRANCH', defaultValue: 'lesson02', description: 'The target git branch')
        choice(name: 'BROWSER_NAME', choices: ['chrome', 'firefox'], description: 'Pick the target browser in Selenoid')
        choice(name: 'BROWSER_VERSION', choices: ['88.0', '87.0', '78.0'], description: 'Pick the target browser version in Selenoid')
    }

    stages {
        stage('Pull from GitHub') {
            steps {
                git ([
                    url: "${params.GIT_URL}",
                    branch: "${params.GIT_BRANCH}"
                    ])
            }
        }
        stage('Run maven clean test') {
            steps {
                sh 'mvn clean test -Dbrowser_name=$BROWSER_NAME -Dbrowser_version=$BROWSER_VERSION'
            }
        }
        stage('Backup and Reports') {
            steps {
                archiveArtifacts artifacts: '**/target/', fingerprint: true
            }
            post {
                always {
                  script {
                    // Формирование отчета
                    allure([
                      includeProperties: false,
                      jdk: '',
                      properties: [],
                      reportBuildPolicy: 'ALWAYS',
                      results: [[path: 'target/allure-results']]
                    ])
                    println('allure report created')

                    // Узнаем ветку репозитория
                    def branch = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD\n').trim().tokenize().last()
                    println("branch= " + branch)

                    // Достаем информацию по тестам из junit репорта
                    def summary = junit testResults: '**/target/surefire-reports/*.xml'
                    println("summary generated")

                    // Текст оповещения
                    def message = "${currentBuild.currentResult}: Job ${env.JOB_NAME}, build ${env.BUILD_NUMBER}, branch ${branch}\nTest Summary - ${summary.totalCount}, Failures: ${summary.failCount}, Skipped: ${summary.skipCount}, Passed: ${summary.passCount}\nMore info at: ${env.BUILD_URL}"
                    println("message= " + message)

                    // Отправка результатов на почту
                    emailext body: "${message}",
                        recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                        subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}",
                        to: 'tralivali.vali@yandex.ua'

                    // Отправка результатов В slack
                    slackSend(
                        message: "${message}",
                        channel: "for-jenkins"
                    )
                  }
                }
            }
        }
    }
}