/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.2.2"
    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.1.5"
    const val junit = "junit:junit:4.13"
    const val okhttp3 = "com.squareup.okhttp3:okhttp:4.9.1"
    const val androidEventBus = "org.simple:androideventbus:1.0.5.1"
    const val gson = "com.google.code.gson:gson:2.8.7"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val pictureSelector = "io.github.lucksiege:pictureselector:v2.7.3-rc09"
    const val bigImageViewPager = "com.github.SherlockGougou:BigImageViewPager:androidx-6.1.6"

    object Glide {
        private const val version = "4.12.0"

        const val glide = "com.github.bumptech.glide:glide:$version"
        const val glideCompiler = "com.github.bumptech.glide:compiler:$version"
        const val glideLoaderOkhttp = "com.github.bumptech.glide:okhttp3-integration:$version"
    }

    object UMeng {
        const val umeng = "com.umeng.umsdk:common:9.4.0"
        const val umengAsms = "com.umeng.umsdk:asms:1.2.3"
        const val umengApm = "com.umeng.umsdk:apm:1.2.0"
    }

    object Dagger {
        private const val version = "2.37"

        const val dagger = "com.google.dagger:dagger:$version"
        const val daggerAndroid = "com.google.dagger:dagger-android:$version"
        const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:$version"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:$version"
        const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:$version"

    }

    object Accompanist {
        private const val version = "0.16.0"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:$version"
        const val retrofitAdapterRxjava = "com.squareup.retrofit2:adapter-rxjava3:$version"

    }

    object RxJava {
        private const val version = "3.0.13"
        const val rxjava3 = "io.reactivex.rxjava3:rxjava:$version"
        const val rxAndroid3 = "io.reactivex.rxjava3:rxandroid:3.0.0"
        const val rxPermissions = "com.github.tbruyelle:rxpermissions:0.12"
        const val rxerrorhandler2 = "me.jessyan:rxerrorhandler:2.1.1"

        object RxLifecycle {
            private const val version = "4.0.2"

            const val rxlifecycle4 = "com.trello.rxlifecycle4:rxlifecycle:$version"
            const val rxlifecycle4Android = "com.trello.rxlifecycle4:rxlifecycle-android:$version"

        }
    }

    object Coroutines {
        private const val version = "1.5.1"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.3.1"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.0"
        const val coreKtx = "androidx.core:core-ktx:1.6.0"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.1"
        }

        object Compose {
            const val snapshot = ""
            const val version = "1.0.1"

            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val test = "androidx.compose.ui:ui-test:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:${version}"
            const val viewBinding = "androidx.compose.ui:ui-viewbinding:$version"
        }

        object Navigation {
            private const val version = "2.3.4"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
        }

        object Lifecycle {
            private const val version = "2.3.1"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
        }

    }
}

object Urls {
    const val composeSnapshotRepo = "https://androidx.dev/snapshots/builds/" +
            "${Libs.AndroidX.Compose.snapshot}/artifacts/repository/"
    const val accompanistSnapshotRepo = "https://oss.sonatype.org/content/repositories/snapshots"
}
