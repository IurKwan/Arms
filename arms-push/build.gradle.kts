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

    kotlinOptions {
        jvmTarget = Dep.kotlinJvmTarget
    }

    defaultConfig {
        minSdk = Dep.minSdk
        targetSdk = Dep.targetSdk
    }

}

dependencies {
    implementation(Dep.Kotlin.stdlib)
    implementation(Dep.AndroidX.coreKtx)
    implementation(Dep.AndroidX.appcompat)
    compileOnly("com.krbb.arms:sqlbrite:1.0.0")
    implementation(Dep.Google.gson)
    implementation(Dep.RxJava.rxjava3)
    implementation(Dep.RxJava.rxAndroid3)
    implementation("android.arch.persistence:db-framework:1.1.1")
}