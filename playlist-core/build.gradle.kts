import com.iur.plugin.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    `maven-publish`
    signing
    id("arm-publish")
    id("kotlin-parcelize")
}

android {
    compileSdk = Dep.compileSdk
    compileOptions {
        sourceCompatibility = Dep.javaVersion
        targetCompatibility = Dep.javaVersion
    }

    defaultConfig {
        minSdk = Dep.minSdk
        targetSdk = Dep.targetSdk
    }

    kotlinOptions {
        jvmTarget = Dep.kotlinJvmTarget
    }
}

dependencies {
    // Kotlin
    implementation(Dep.Kotlin.stdlib)
    // Android
    implementation(Dep.AndroidX.appcompat)

    implementation("androidx.media:media:1.4.3")

}