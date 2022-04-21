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

    kotlinOptions {
        jvmTarget = Dep.kotlinJvmTarget
    }

    defaultConfig {
        minSdk = Dep.minSdk
        targetSdk = Dep.targetSdk
    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    //support
    api(Dep.Google.material)
    api(Dep.AndroidX.appcompat)

    //rx
    api(Dep.RxJava.rxjava3)
    api(Dep.RxJava.rxAndroid3)
    api(Dep.RxJava.rxlifecycle4)
    api(Dep.RxJava.rxlifecycle4Android)
    api(Dep.Arms.rxerrorhandler)

    //network
    api(Dep.Retrofit.retrofit)
    implementation(Dep.Retrofit.retrofitConverterGson)
    implementation(Dep.Retrofit.retrofitAdapterRxjava)
    api(Dep.Retrofit.okhttp3)

    // tools
    api(Dep.Dagger.dagger)
    kapt(Dep.Dagger.daggerCompiler)
    annotationProcessor(Dep.Dagger.daggerCompiler)

    api(Dep.Google.gson)
    //test
    api(Dep.AndroidX.timber)
    // 选择图片库
    api(Dep.PictureSelector.pictureSelector)
    api(Dep.PictureSelector.pictureSelectorCompress)
    api(Dep.PictureSelector.pictureSelectorUcrop)
    // 查看大图
    api(Dep.Github.bigImageViewPager)
    // 友盟
    compileOnly(Dep.Umeng.umeng)
    compileOnly(Dep.Umeng.umengApm)
    compileOnly(Dep.Umeng.umengAsms)

    // 协程
    api(Dep.Coroutines.core)
    api(Dep.Coroutines.android)
    api("android.arch.lifecycle:livedata:1.1.1")
    // utils
    api("com.blankj:utilcodex:1.31.0")
}