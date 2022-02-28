import com.iur.plugin.Dep

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

    }

}

dependencies {
    implementation(Dep.Kotlin.stdlib)
    implementation(Dep.AndroidX.appcompat)
    api(Dep.Github.glide)
    kapt(Dep.Github.glideCompiler)
//    {
//        exclude module: 'jsr305'
//    }
    compileOnly(project(":arms"))
}