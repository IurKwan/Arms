import com.iur.plugin.Dep

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    `maven-publish`
    id("arm-publish")
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

    }

}

dependencies {
    compileOnly(Dep.RxJava.rxjava3)
}