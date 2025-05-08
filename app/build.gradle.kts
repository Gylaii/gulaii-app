plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.compose.compiler)
  kotlin("plugin.serialization")

}

android {
  namespace = "org.gulaii.app"
  compileSdk = 35

  defaultConfig {
    applicationId = "org.gulaii.app"
    minSdk = 26
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  buildFeatures {
    compose = true
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.test.junit4.android)
  implementation(libs.androidx.navigation.compose)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")

  val composeBom = platform("androidx.compose:compose-bom:2025.02.00")
  implementation(composeBom)
  androidTestImplementation(composeBom)

  // Choose one of the following:
  // Material Design 3
  implementation("androidx.compose.material3:material3")
  // or Material Design 2
  implementation("androidx.compose.material:material")
  // or skip Material Design and build directly on top of foundational components
  implementation("androidx.compose.foundation:foundation")
  // or only import the main APIs for the underlying toolkit systems,
  // such as input and measurement/layout
  implementation("androidx.compose.ui:ui")

  // Android Studio Preview support
  implementation("androidx.compose.ui:ui-tooling-preview")
  debugImplementation("androidx.compose.ui:ui-tooling")

  // UI Tests
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  debugImplementation("androidx.compose.ui:ui-test-manifest")

  // Optional - Included automatically by material, only add when you need
  // the icons but not the material library (e.g. when using Material3 or a
  // custom design system based on Foundation)
  implementation("androidx.compose.material:material-icons-core")
  // Optional - Add full set of material icons
  implementation("androidx.compose.material:material-icons-extended")
  // Optional - Add window size utils
  implementation("androidx.compose.material3.adaptive:adaptive")

  // Optional - Integration with activities
  implementation("androidx.activity:activity-compose:1.10.0")
  // Optional - Integration with ViewModels
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
  // Optional - Integration with LiveData
  implementation("androidx.compose.runtime:runtime-livedata")
  // Optional - Integration with RxJava
  implementation("androidx.compose.runtime:runtime-rxjava2")
// https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json-jvm
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.8.0")

  // Retrofit core
  implementation("com.squareup.retrofit2:retrofit:2.9.0")

  implementation("androidx.datastore:datastore-preferences:1.1.0")

  // Kotlinx-Serialization конвертер для Retrofit
  implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

  // OkHttp + логирующий интерцептор
  implementation("com.squareup.okhttp3:okhttp:4.11.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
}
