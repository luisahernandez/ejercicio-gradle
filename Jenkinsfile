pipeline {
    agent any
    environment {
        NEXUS_USER         = credentials('user-nexus')
        NEXUS_PASSWORD     = credentials('password-nexus')
    }
    parameters {
        choice(
            name:'compileTool',
            choices: ['Maven', 'Gradle'],
            description: 'Seleccione herramienta de compilacion'
        )
    }
    stages {
        stage("Pipeline"){
            steps {
                script{
                  switch(params.compileTool)
                    {
                        case 'Maven':
                            //def ejecucion = load 'maven.groovy'
                            //ejecucion.call()
                            echoo 'error forzado';
                        break;
                        case 'Gradle':
                            def ejecucion = load 'gradle.groovy'
                            ejecucion.call()
                        break;
                    }
                }
            }
        post {
                always {
                    sh "echo 'fase always executed post'"
                }
                success {
                    slackSend teamDomain: 'dipdevopsusac-tr94431', tokenCredentialId: 'slack-me',message: 'luisa hernandez'+" "+ env.JOB_NAME+ env.BUILD_NUMBER+" "+'Operacion Exitosa'
                }
                failure {
                    slackSend teamDomain: 'dipdevopsusac-tr94431', tokenCredentialId: 'slack-me',message: 'luisa hernandez'+" "+env.JOB_NAME+ env.BUILD_NUMBER+" "+'Operacion Fallida'
                }
            }
        }
    }
}