import com.iur.plugin.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    `maven-publish`
    signing
    id("arm-publish")
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

}

dependencies {
    implementation(Dep.Kotlin.stdlib)

    implementation("androidx.media:media:1.4.3")

    implementation(Dep.AndroidX.appcompat)

    implementation("androidx.constraintlayout:constraintlayout:2.1.1")

    // ExoPlayer
    implementation("com.google.android.exoplayer:exoplayer-core:2.17.1")
    implementation("com.google.android.exoplayer:exoplayer-dash:2.17.1")
    implementation("com.google.android.exoplayer:exoplayer-hls:2.17.1")
    implementation("com.google.android.exoplayer:exoplayer-smoothstreaming:2.17.1")
}
