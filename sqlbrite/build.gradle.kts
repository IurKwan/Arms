import com.iur.plugin.Dep

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    `maven-publish`
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

//  lintOptions {
//    textOutput 'stdout'
//    textReport true
//  }

    // TODO replace with https://issuetracker.google.com/issues/72050365 once released.
//  libraryVariants.all {
//    it.generateBuildConfig.enabled = false
//  }

}