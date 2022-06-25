plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    kotlin("plugin.serialization").version("1.6.21")
}
apply {
    plugin("kotlin-android")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {
      implementation(AppDependencies.AndroidDependencies.hiltAndroidDependency)
      api(AppDependencies.ThirdPartDependencies.chukerAndroidDependency)
      kapt(AppDependencies.AndroidDependencies.hiltCompilerDependency)
      api(AppDependencies.KotlinDependencies.ktorClientCoreDependency)
      api(AppDependencies.KotlinDependencies.ktorOkHttpClientDependency)
      api(AppDependencies.KotlinDependencies.ktorJsonSerializerDependency)
      api(AppDependencies.KotlinDependencies.ktorLoggingDependency)
      api(AppDependencies.KotlinDependencies.ktorContentNegotiationDependency)
}