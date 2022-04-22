import com.iur.plugin.Dep

plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
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

dependencies {
  compileOnly(kotlin("stdlib-jdk8"))
  compileOnly("com.android.tools.lint:lint-api:30.1.3")
}

//jar {
//  manifest {
//    attributes("Lint-Registry-v2": "com.squareup.sqlbrite3.BriteIssueRegistry")
//  }
//}
