/*
SLACK_CHANNEL=redbull
RELEASE_BRANCH=release
 */

/* current pom version of project */
def pomVersion

/* This method will notify when the job run fail at any stage */
def notifyFailedToSlack() {
    slackSend (channel: SLACK_CHANNEL, color: '#FF0000', message: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL}) build failed")
}

/* This method will notify when the job run sucessfully */
def notifySuccessToSlack() {
    slackSend (channel: SLACK_CHANNEL, color: '#32CD32', message: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL}) build successfully")
}

/* This method will notify when the job started */
def notifyBeginBuildToSlack() {
    slackSend (channel: SLACK_CHANNEL, color: '#0096d6', message: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' started")
}


/*
	This Pipelines are made up of multiple steps on multiple server. It has 2 node of 2 server: Master Server and Dev Server
	Master Server will build the image of wildfly and push to dockerland
	Dev Server will pull the image from dockerland and run it
	It also send a notification by slack to who has permission to build the job.
*/
try {
    node('master'){

        /* Stage start, will notify to slack channel of team that run job */
        stage('Start'){
            notifyBeginBuildToSlack()
        }

        /* Stage checkout, will get the source code from git server */
        stage('Checkout'){
            checkout scm
            sh "git checkout ${RELEASE_BRANCH}"
            sh 'git pull'
            pomVersion = readMavenPom().getVersion()
        }

        stage('Merge release branch into master') {
            sh "git fetch origin ${RELEASE_BRANCH}"
            sh "git checkout ${RELEASE_BRANCH}"
            sh "git fetch origin master"
            sh "git checkout master"
            sh "git branch"
            sh "git merge ${RELEASE_BRANCH} --strategy-option theirs"
            sh "git add ."
            sh "git commit -m 'Create tag ${pomVersion}' || true"
            sh "git push -u origin master"
        }

        stage('Create release tag from master branch') {
            sh "git tag ${pomVersion}"
            sh "git push origin ${pomVersion}"
        }

        stage('Delete release branch') {
            sh "git push origin --delete ${RELEASE_BRANCH}"
        }

        /* Stage post build, if no error, notify success to slack channel */
        stage('Finish') {
            notifySuccessToSlack()
        }
    }
} catch (e) {
    /* if error notify error to slack channel */
    notifyFailedToSlack()
    currentBuild.result = 'FAILED'
}


