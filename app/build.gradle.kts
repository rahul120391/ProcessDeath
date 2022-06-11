plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
}
apply {
    plugin("kotlin-android")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(AppDependencies.KotlinDependencies.kotlinCoreDependency)
    implementation(AppDependencies.KotlinDependencies.kotlinCoroutinesDependency)
    implementation(AppDependencies.AndroidDependencies.appCompatDependency)
    implementation(AppDependencies.AndroidDependencies.googleMaterialDependency)
    implementation(AppDependencies.AndroidDependencies.constraintLayoutDependency)
    implementation(AppDependencies.AndroidDependencies.navFragmentDependency)
    implementation(AppDependencies.AndroidDependencies.navUiDependency)
    implementation(AppDependencies.AndroidDependencies.lifecycleRuntimeDependency)
    implementation(AppDependencies.AndroidDependencies.liveDataDependency)
    implementation(AppDependencies.AndroidDependencies.viewModelDependency)
    implementation(AppDependencies.AndroidDependencies.viewModelSavedDataDependency)
    implementation(AppDependencies.AndroidDependencies.coroutinesAndroidDependency)
    testImplementation(AppDependencies.UnitTestDependencies.junitDependency)
    androidTestImplementation(AppDependencies.UnitTestDependencies.androidXEspressoDependency)
    androidTestImplementation(AppDependencies.UnitTestDependencies.androidXJunitExtensionDependency)
    implementation(AppDependencies.AndroidDependencies.hiltAndroidDependency)
    kapt(AppDependencies.AndroidDependencies.hiltCompilerDependency)
    implementation(project(":domain"))
}