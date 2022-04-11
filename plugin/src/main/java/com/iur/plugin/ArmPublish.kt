package com.iur.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.plugins.signing.SigningExtension
import java.util.*
import kotlin.io.*

class ArmPublish : Plugin<Project> {

    override fun apply(project: Project) {

        val isAndroid = project.hasProperty("android")

        if (isAndroid) {
            println("android")
            val android = project.extensions.getByName("android") as LibraryExtension
            android.publishing {
                singleVariant("release") {
                    withJavadocJar()
                    withSourcesJar()
                }
            }

        } else {
            println("java/kotlin")
            project.configure<JavaPluginExtension> {
                withSourcesJar()
                withJavadocJar()
            }
        }

        project.afterEvaluate {
            val properties = Properties()
            val selfFile = project.file("gradle.properties")
            val file = project.rootProject.file("gradle.properties")
            if (file.exists() && selfFile.exists()) {
                // 声明变量记录maven库地址
                var mavenRepositoryUrl = ""
                properties.load(selfFile.inputStream())
                properties.load(file.inputStream())
                if (properties.getProperty("VERSION_NAME").contains("SNAPSHOT")){
                    // 下面的库地址指向的是我们私有仓库的snapshots 仓库
                    mavenRepositoryUrl = properties.getProperty("SNAPSHOT_REPOSITORY_URL")
                } else {
                    // 下面的库地址指向的是我们私有仓库的Releases 仓库
                    mavenRepositoryUrl = properties.getProperty("RELEASE_REPOSITORY_URL")
                }

                val mavenUsername = properties.getProperty("NEXUS_USERNAME")
                val mavenPassword = properties.getProperty("NEXUS_PASSWORD")

                println("mavenUrl:$mavenRepositoryUrl")
                println("mavenUsername:$mavenUsername")
                println("mavenPassword:$mavenPassword")

                project.configure<PublishingExtension> {
                    repositories {
                        maven {
                            isAllowInsecureProtocol = true
                            setUrl(mavenRepositoryUrl)
                            credentials {
                                username = mavenUsername
                                password = mavenPassword
                            }
                        }
                    }
                    publications {
                        create<MavenPublication>("release") {

//                            project.configure<SigningExtension> {
//                                sign(this@create)
//                            }

                            if (isAndroid) {
                                from(components.getByName("release"))
                            } else {
                                from(components.getByName("java"))
                            }

                            groupId = properties.getProperty("POM_GROUP_ID")
                            artifactId = properties.getProperty("POM_ARTIFACT_ID")
                            version = properties.getProperty("VERSION_NAME")

                            pom {
                                name.set(properties.getProperty("POM_NAME"))
                                url.set(properties.getProperty("POM_URL"))
                                licenses {
                                    license {
                                        name.set(properties.getProperty("POM_LICENCE_NAME"))
                                        url.set(properties.getProperty("POM_LICENCE_URL"))
                                    }
                                }
                                developers {
                                    developer {
                                        id.set(properties.getProperty("POM_DEVELOPER_ID"))
                                        name.set(properties.getProperty("POM_DEVELOPER_NAME"))
                                        email.set(properties.getProperty("guanzhirui@gmail.com"))
                                    }
                                }
                                scm {
                                    connection.set(properties.getProperty("POM_SCM_CONNECTION"))
                                    developerConnection.set(properties.getProperty("POM_SCM_DEV_CONNECTION"))
                                    url.set(properties.getProperty("POM_SCM_URL"))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun println(log: String) {
    kotlin.io.println("arm publish > $log")
}