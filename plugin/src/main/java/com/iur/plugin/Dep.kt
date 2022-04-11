package com.iur.plugin

import org.gradle.api.JavaVersion

/**
 * @author IurKwan
 * @date 2022/2/27
 */
object Dep {
    val javaVersion = JavaVersion.VERSION_1_8
    const val kotlinJvmTarget = "1.8"
    const val compileSdk = 30
    const val minSdk = 19
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.2.15"


    object Arm {
        const val group = "com.iur"
    }

    object Compose {
        val version = "1.1.1"
        val animation = "androidx.compose.animation:animation:$version"
        val ui = "androidx.compose.ui:ui:$version"
        val material = "androidx.compose.material:material:$version"
        val compiler = "androidx.compose.compiler:compiler:$version"
        val activity = "androidx.activity:activity-compose:1.4.0"
        val constraintlayout = "androidx.constraintlayout:constraintlayout-compose:1.0.0"

        val pager = "com.google.accompanist:accompanist-pager:0.24.0-alpha"
    }

    object Kotlin {
        private const val version = "1.6.10"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Google {
        const val material = "com.google.android.material:material:1.4.0"
        const val gson = "com.google.code.gson:gson:2.8.7"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.3.1"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.0"
        const val coreKtx = "androidx.core:core-ktx:1.6.0"

        const val androidEventBus = "org.simple:androideventbus:1.0.5.1"
        const val timber = "com.jakewharton.timber:timber:4.7.1"
    }

    object RxJava {

        private const val version = "3.0.13"
        const val rxjava3 = "io.reactivex.rxjava3:rxjava:$version"
        const val rxAndroid3 = "io.reactivex.rxjava3:rxandroid:3.0.0"
        const val rxerrorhandler2 = "me.jessyan:rxerrorhandler:2.1.1"


        private const val rxlifecycleVersion = "4.0.2"
        const val rxlifecycle4 = "com.trello.rxlifecycle4:rxlifecycle:$rxlifecycleVersion"
        const val rxlifecycle4Android = "com.trello.rxlifecycle4:rxlifecycle-android:$rxlifecycleVersion"
    }

    object Retrofit {

        private const val version = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:$version"
        const val retrofitAdapterRxjava = "com.squareup.retrofit2:adapter-rxjava3:$version"

        const val okhttp3 = "com.squareup.okhttp3:okhttp:4.9.1"

    }

    object Dagger {

        private const val version = "2.37"

        const val dagger = "com.google.dagger:dagger:$version"
        const val daggerAndroid = "com.google.dagger:dagger-android:$version"
        const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:$version"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:$version"
        const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:$version"


    }

    object Github {

        private const val version = "4.12.0"

        const val glide = "com.github.bumptech.glide:glide:$version"
        const val glideCompiler = "com.github.bumptech.glide:compiler:$version"
        const val glideLoaderOkhttp = "com.github.bumptech.glide:okhttp3-integration:$version"

        const val bigImageViewPager = "com.github.SherlockGougou:BigImageViewPager:androidx-6.2.1"

    }

    object PictureSelector {
        private const val version = "v3.0.9"

        const val pictureSelector = "io.github.lucksiege:pictureselector:$version"
        const val pictureSelectorCompress = "io.github.lucksiege:compress:$version"
        const val pictureSelectorUcrop = "io.github.lucksiege:ucrop:$version"
    }

    object Umeng {
        const val umeng = "com.umeng.umsdk:common:9.4.4"
        const val umengAsms = "com.umeng.umsdk:asms:1.4.1"
        const val umengApm = "com.umeng.umsdk:apm:1.5.2"
    }


    object Coroutines {
        private const val version = "1.5.0"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Arms {
        const val rxerrorhandler = "com.krbb.arms:rxerrorhandler:1.0.0"
    }

}