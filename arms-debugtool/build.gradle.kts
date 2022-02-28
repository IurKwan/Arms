import com.iur.plugin.Dep
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Dep.compileSdk
    compileOptions {
        sourceCompatibility = Dep.javaVersion
        targetCompatibility = Dep.javaVersion
    }

    kotlinOptions {
        jvmTarget = Dep.kotlinJvmTarget
    }

    defaultConfig {
        minSdk = Dep.minSdk
        targetSdk = Dep.targetSdk
    }


    buildTypes {
        debug {
            buildConfigField("String", "BUILD_TIME", "\"" + buildTime() + "\"")
        }
    }

}

fun buildTime(): String {
    return Date().toString()
}

dependencies {
    implementation(Dep.Kotlin.stdlib)
    implementation(Dep.AndroidX.appcompat)
    implementation(Dep.Compose.material)
}