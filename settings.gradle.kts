pluginManagement {
    repositories {
        mavenCentral()
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

dependencyResolutionManagement {
    repositories {
        maven { setUrl("https://repo1.maven.org/maven2/") }
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("http://maven.aliyun.com/nexus/content/repositories/jcenter") }
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

includeBuild("../plugin")

include(":app")
include(":arms")
include(":arms-push")
include(":arms-imageloader-glide")
include(":arms-rxerrorhandler")
include(":sqlbrite")