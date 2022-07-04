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

    composeOptions {
        kotlinCompilerExtensionVersion = Dep.Compose.version
    }

    defaultConfig {
        applicationId = "com.iur.arms"
        minSdk = Dep.minSdk
        targetSdk = Dep.targetSdk
        versionCode = Dep.versionCode
        versionName = Dep.versionName

        ndk {
            abiFilters.add("arm64-v8a")
        }

        multiDexEnabled = true
    }

    buildTypes {

    }

}

dependencies {
    implementation(Dep.Kotlin.stdlib)
    implementation(Dep.Google.material)
    implementation(Dep.AndroidX.coreKtx)
    implementation(Dep.AndroidX.constraintlayout)
    implementation(project(":arms"))
    implementation(project(":arms-push"))
    implementation(project(":arms-imageloader-glide"))
    implementation("com.android.support:multidex:2.0.0")
}