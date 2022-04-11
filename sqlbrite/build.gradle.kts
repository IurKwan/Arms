import com.iur.plugin.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    `maven-publish`
    signing
    id("arm-publish")
}

dependencies {
    implementation(Dep.RxJava.rxjava3)
    implementation("android.arch.persistence:db:1.1.1")
    lintChecks(project(":sqlbrite-lint"))

}

android {
    compileSdk = Dep.compileSdk

    defaultConfig {
        minSdk = Dep.minSdk
        targetSdk = Dep.targetSdk
    }

    compileOptions {
        sourceCompatibility = Dep.javaVersion
        targetCompatibility = Dep.javaVersion
    }

}