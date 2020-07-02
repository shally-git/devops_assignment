pipeline
{
    agent any
    tools
    {
        maven 'maven'
    }
    
    stages
    {
        stage('checkout'){
            steps{
                echo "checkout"
                git branch:"master", url:"https://github.com/shally-git/devops_assignment"
				//checkout scm
            }
        }
        
        stage('build'){
            steps{
                echo "build"
                bat "mvn clean install"
            }
        }
        
        stage('sonar'){
            steps{
                echo "sonar"
                withSonarQubeEnv("local sonar") 
				{
					bat "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar"
				}
            }
        }
		
		stage ('Upload to Artifactory')
		{
			steps
			{
				rtMavenDeployer (
                    id: 'deployer',
                    serverId: 'art_devops_assignment',
                    releaseRepo: 'devopsforqa_assignment_Shally',
                    snapshotRepo: 'devopsforqa_assignment_Shally'
                )
                rtMavenRun (
                    pom: 'pom.xml',
                    goals: 'clean install',
                    deployerId: 'deployer',
                )
                rtPublishBuildInfo (
                    serverId: 'art_devops_assignment',
                )
			}
		}
        
    }
}
