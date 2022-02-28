import com.iur.plugin.Dep

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    `maven-publish`
    id("arm-publish")
//    kotlin("parcelize")
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
//    {
//        exclude module : 'jsr305'
//    }
    api(Dep.RxJava.rxlifecycle4Android)
//    {
//        exclude module : 'support-annotations'
//        exclude module : 'rxjava'
//        exclude module : 'rxandroid'
//        exclude module : 'rxlifecycle'
//    }
    api("com.krbb.arms:rxerrorhandler:1.0.0")

    //network
    api(Dep.Retrofit.retrofit)
//    {
//        exclude module : 'okhttp'
//        exclude module : 'okio'
//    }
    implementation(Dep.Retrofit.retrofitConverterGson)
//    {
//        exclude module : 'gson'
//        exclude module : 'okhttp'
//        exclude module : 'okio'
//        exclude module : 'retrofit'
//    }
    implementation(Dep.Retrofit.retrofitAdapterRxjava)
//    {
//        exclude module : 'rxjava'
//        exclude module : 'okhttp'
//        exclude module : 'retrofit'
//        exclude module : 'okio'
//    }
    api(Dep.Retrofit.okhttp3)

    // tools
    // compileOnly rootProject.ext.dependencies["javax.annotation"]
    api(Dep.Dagger.dagger)
    kapt(Dep.Dagger.daggerCompiler)
    annotationProcessor(Dep.Dagger.daggerCompiler)

    api(Dep.Google.gson)
    //test
    api(Dep.AndroidX.timber)
    // 选择图片库
    api(Dep.Github.pictureSelector)
    // 查看大图
    api(Dep.Github.bigImageViewPager)
//    {
//        exclude module : 'subsampling-scale-image-view'
//    }
    // 友盟
    compileOnly(Dep.Umeng.umeng)
    compileOnly(Dep.Umeng.umengApm)
    compileOnly(Dep.Umeng.umengAsms)

    // 协程
    api(Dep.Coroutines.core)
    api(Dep.Coroutines.android)
    api("android.arch.lifecycle:livedata:1.1.1")

//    configurations.all {
//        resolutionStrategy.force ('androidx.fragment:fragment:1.1.0')
//    }
}