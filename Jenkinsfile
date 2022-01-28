import groovy.json.JsonSlurperClassic
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}
pipeline {
    agent any
    stages {
        stage("Paso 0: Download and checkout"){
            steps {
                checkout(
                    [$class: 'GitSCM',
                    //Acá reemplazar por el nonbre de branch
                    branches: [[name: "feature-nexus" ]],
                    //Acá reemplazar por su propio repositorio
                    userRemoteConfigs: [[url: 'https://github.com/luisahernandez/ejemplo-maven.git']]])
            }
        }
        stage("Paso 1: Compilaar"){
            steps {
                script {
                sh "echo 'Compile Code!'"
                // Run Maven on a Unix agent.
                sh "mvn clean compile -e"
                }
            }
        }
        stage("Paso 2: Testear"){
            steps {
                script {
                sh "echo 'Test Code!'"
                // Run Maven on a Unix agent.
                sh "mvn clean test -e"
                }
            }
        }
        stage("Paso 3: Build .Jar"){
            steps {
                script {
                sh "echo 'Build .Jar!'"
                // Run Maven on a Unix agent.
                sh "mvn clean package -e"
                }
            }
        }
        stage("Paso 4: Análisis SonarQube"){
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh "echo 'Calling sonar Service in another docker container!'"
                    // Run Maven on a Unix agent to execute Sonar.
                    sh 'mvn clean verify sonar:sonar'
                }
            }
            post {
                //record the test results and archive the jar file.
                success {
                    //archiveArtifacts artifacts:'build/*.jar'
                    nexusPublisher nexusInstanceId: 'nexus',
                        nexusRepositoryId: 'devops-usach-nexus',
                        packages: [
                            [$class: 'MavenPackage',
                                mavenAssetList: [
                                    [classifier: '',
                                    extension: '.jar',
                                    filePath: 'build/DevOpsUsach2020-0.0.8.jar']
                                ],
                        mavenCoordinate: [
                            artifactId: 'DevOpsUsach2020',
                            groupId: 'com.devopsusach2020',
                            packaging: 'jar',
                            version: '0.0.8']
                        ]
                    ]
                }
            }
        }
		stage("Download: Nexus"){
            steps {
                //http://nexus:10001/repository/devops-usach-nexus/com/devopsusach2020/DevOpsUsach2020/0.0.8/DevOpsUsach2020-0.0.8.jar
                sh ' curl -X GET -u admin:admin "http://nexus:8081/repository/devops-usach-nexus/com/devopsusach2020/DevOpsUsach2020/0.0.8/DevOpsUsach2020-0.0.8.jar" -O'
            }
        }
        stage("Run: Levantar Springboot APP"){
            steps {
                sh 'nohup bash java -jar DevOpsUsach2020-0.0.8.jar & >/dev/null'
            }
        }
        stage("Curl: Dormir(Esperar 20sg) "){
            steps {
               sh "sleep 20 && curl -X GET 'http://nexus:8081/rest/mscovid/test?msg=testing'"
            }
        }
        stage("Subir nueva Version"){
            steps {
                //archiveArtifacts artifacts:'build/*.jar'
                nexusPublisher nexusInstanceId: 'nexus',
                    nexusRepositoryId: 'devops-usach-nexus',
                    packages: [
                        [$class: 'MavenPackage',
                            mavenAssetList: [
                                [classifier: '',
                                extension: '.jar',
                                filePath: 'build/DevOpsUsach2020-0.0.8.jar']
                            ],
                    mavenCoordinate: [
                        artifactId: 'DevOpsUsach2020',
                        groupId: 'com.devopsusach2020',
                        packaging: 'jar',
                        version: '1.0.0']
                    ]
                ]
            }
        }
    }
    post {
        always {
            sh "echo 'fase always executed post'"
        }
        success {
            sh "echo 'fase success'"
        }
        failure {
            sh "echo 'fase failure'"
        }
    }
}