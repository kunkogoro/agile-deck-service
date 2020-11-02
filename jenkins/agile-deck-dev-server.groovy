/*
SLACK_CHANNEL

 */

/* variable to get and set failed stage */
def FAILED_STAGE

/* quality gate status */
def qg

/* current pom version of project */
def pomVersion



/* This method will notify when the job run fail at any stage */
def notifyFailedToSlack() {
	slackSend (channel: 'redbull', color: '#FF0000', message: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL}) build failed")
}

/* This method will notify when the job run sucessfully */
def notifySuccessToSlack() {
	slackSend (channel: 'redbull', color: '#32CD32', message: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL}) build successfully")
}

/* This method will notify when the job started */
def notifyBeginBuildToSlack() {
	slackSend (channel: 'redbull', color: '#0096d6', message: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' started")
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
			FAILED_STAGE = 'Checkout'
			checkout scm
			sh 'git checkout develop'
			sh 'git pull'
			// Get current pom version after checkout the project
			pomVersion = readMavenPom().getVersion()
		}

		/* Stage build, build the project to generate war file and wildfly image */
		stage('Build'){
			FAILED_STAGE = 'Build'
			withMaven( maven: 'MAVEN 3.6' ) {
//				sh "mvn clean package"
				sh "mvn clean package -Pnative -Dquarkus.native.container-build=true"
			}
		}

		/* Stage check sonar, using sonar to scan the project */
		stage('Check sonar') {
			FAILED_STAGE = 'Check sonar'

			// Using sonar to scan the proejct to check coverage, bugs...
			def scannerHome = tool 'SonarQubeScanner'
			withSonarQubeEnv('SonarQube') {
				sh "${scannerHome}/bin/sonar-scanner -Dsonar.language=java \
					-Dsonar.projectName=${env.JOB_NAME} \
					-Dsonar.projectKey=${env.JOB_NAME} \
					-Dsonar.tests=src/test/java \
					-Dsonar.sources=src/main/java \
					-Dsonar.java.libraries=/var/jenkins_home/.m2/repository/org/projectlombok/lombok/1.18.2/lombok-1.18.2.jar \
					-Dsonar.java.binaries=. \
					-Dsonar.java.coveragePlugin=jacoco"
			}

			// Get report from sonar
			sleep(10)
			timeout(time:15, unit:'MINUTES'){
				qg = waitForQualityGate()
				if (qg.status != 'OK'){
					currentBuild.result = 'FAILURE'
					error "Pipeline aborted due to quality gate failure: ${qg.status}"
				}
			}
		}

		/* Stage build docker image, build the project to image */
		stage('Create image') {
			FAILED_STAGE = 'Create image'
			sh "docker build -f src/main/docker/Dockerfile.native -t ct-redbull/agiledeckserver:${pomVersion} ."
		}

		/* Stage push image to aavn-registry */
		stage('Push image to docker registry') {
			FAILED_STAGE = 'Push image to docker registry'
			docker.withRegistry('https://aavn-registry.axonactive.vn.local/', 'ccad9d2d-0400-4a36-9238-a49a70cf98c7') {
				agileDeckImage = docker.image("ct-redbull/agiledeckserver:${pomVersion}")
				agileDeckImage.push()
			}
		}

		stage('Remove unused images') {
			sh "docker rmi ct-redbull/agiledeckserver:latest || true"
			sh "docker rmi ct-redbull/agiledeckserver:${pomVersion} || true"
			sh "docker rmi aavn-registry.axonactive.vn.local/ct-redbull/agiledeckserver:${pomVersion} || true"
		}

		stage('Pull and run image on Dev server') {
			FAILED_STAGE = 'Pull and run image on Dev server'

			/*ssh to develop server*/
			withCredentials([usernamePassword(credentialsId: 'redbull-control-server', passwordVariable: 'password', usernameVariable: 'username')]) {
				def remote = [:]
				remote.user = "${username}"
				remote.password = "${password}"
				remote.name = "remote-to-agile-deck-server"
				remote.host = "192.168.70.91"
				remote.allowAnyHosts = true

				/*
				Pull and run image on Dev server
				Before build, must stop and remove container that already run. then remove old image in the dev server
				After remove container and image, pull new image from dockerland and rerun the container
				*/
				sshCommand remote: remote, command:  """docker network create agile-deck-network || true"""

				sshCommand remote: remote, command:  """docker stop agiledeckserver-dev || true && docker rm agiledeckserver-dev || true"""

				sshCommand remote: remote, command:  """docker rmi aavn-registry.axonactive.vn.local/ct-redbull/agiledeckserver:${pomVersion} -f || true"""

				withCredentials([usernamePassword(credentialsId: 'ccad9d2d-0400-4a36-9238-a49a70cf98c7', passwordVariable: 'password', usernameVariable: 'username')]) {
					sshCommand remote: remote, command:  """docker login aavn-registry.axonactive.vn.local -u ${username} -p ${password}"""
					sshCommand remote: remote, command:  """docker pull aavn-registry.axonactive.vn.local/ct-redbull/agiledeckserver:${pomVersion}"""
					sshCommand remote: remote, command:  """docker run -i -d --rm -p 8090:8080 --name agiledeckserver-dev aavn-registry.axonactive.vn.local/ct-redbull/agiledeckserver:${pomVersion}"""
					sshCommand remote: remote, command:  """docker network connect agile-deck-network agiledeckserver-dev"""
				}
			}
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


