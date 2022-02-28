pluginManagement {
    repositories {
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        maven { setUrl("https://repo1.maven.org/maven2/") }
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("http://maven.aliyun.com/nexus/content/repositories/jcenter") }
        // 本地依赖库
        maven { setUrl("http://120.24.83.94:8081/repository/krbb-snapshots/") }
        maven { setUrl("http://120.24.83.94:8081/repository/krbb-releases/") }
    }
}

includeBuild("plugin")

include(":app")
include(":arms")
include(":arms-debugtool")
include(":arms-push")
include(":arms-imageloader-glide")
include(":arms-rxerrorhandler")
include(":sqlbrite-lint")
include(":sqlbrite")