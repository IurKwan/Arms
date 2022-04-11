// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("arm-dep")
}

buildscript {

    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven { setUrl("https://jitpack.io") }
        maven {
            isAllowInsecureProtocol = true
            credentials {
                username = "admin"
                password = "asd789zxc4"
            }
            setUrl("http://120.24.83.94:8081/repository/krbb-snapshots/")
        }
        maven {
            isAllowInsecureProtocol = true
            credentials {
                username = "admin"
                password = "asd789zxc4"
            }
            setUrl("http://120.24.83.94:8081/repository/krbb-releases/")
        }
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }

}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven { setUrl("https://jitpack.io") }
        maven {
            isAllowInsecureProtocol = true
            credentials {
                username = "admin"
                password = "asd789zxc4"
            }
            setUrl("http://120.24.83.94:8081/repository/krbb-snapshots/")
        }
        maven {
            isAllowInsecureProtocol = true
            credentials {
                username = "admin"
                password = "asd789zxc4"
            }
            setUrl("http://120.24.83.94:8081/repository/krbb-releases/")
        }
    }
}

subprojects {
    project.configurations.all {
        resolutionStrategy {
            force("androidx.fragment:fragment:1.1.0")
        }
    }
}