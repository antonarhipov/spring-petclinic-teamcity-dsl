import jetbrains.buildServer.configs.kotlin.v2018_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_2.Project
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2018_2.project
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.v2018_2.version

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2018.2"

project {
    val vcsRoot = git {
        id("PetclinicVcs")
        name = "PetclinicVcs"
        url = "https://github.com/spring-projects/spring-petclinic.git"
    }

    configuration {
        id("Build")
        name = "Build"
        artifactRules = "target/*jar"

        vcs {
            root(vcsRoot)
        }
        steps {
            maven {
                goals = "clean package"
            }
        }
        triggers {
            vcs { }
        }
    }
}

//object Build: BuildType({
//    name = "Build"
//    artifactRules = "target/*jar"
//
//    vcs {
//        root(PetclinicVcs)
//    }
//    steps {
//        maven {
//            goals = "clean package"
//        }
//    }
//    triggers {
//        vcs { }
//    }
//})
//
//object PetclinicVcs : GitVcsRoot({
//    name = "PetclinicVcs"
//    url = "https://github.com/spring-projects/spring-petclinic.git"
//})

fun Project.configuration(bt: BuildType.() -> Unit) = buildType(bt)
fun Project.git(vcs: GitVcsRoot.()->Unit) = GitVcsRoot(vcs)
