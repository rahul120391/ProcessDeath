object AppDependencies {

    object KotlinDependencies{
        const val kotlinCoreDependency = "androidx.core:core-ktx:${DependencyVersioning.KotlinDependenciesVersions.kotlinCoreKtxVersion}"
        const val kotlinCoroutinesDependency = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependencyVersioning.KotlinDependenciesVersions.kotlinCoroutinesCoreVersion}"
        const val ktorClientCoreDependency = "io.ktor:ktor-client-core:${DependencyVersioning.KotlinDependenciesVersions.ktorVersion}"
        const val ktorClientCIODependency  = "io.ktor:ktor-client-cio:${DependencyVersioning.KotlinDependenciesVersions.ktorVersion}"
        const val ktorJsonSerializerDependency = "io.ktor:ktor-serialization-kotlinx-json:${DependencyVersioning.KotlinDependenciesVersions.ktorVersion}"
        const val ktorLoggingDependency  ="io.ktor:ktor-client-logging:${DependencyVersioning.KotlinDependenciesVersions.ktorVersion}"
        const val ktorContentNegotiationDependency = "io.ktor:ktor-client-content-negotiation:${DependencyVersioning.KotlinDependenciesVersions.ktorVersion}"
        const val gsonDependency = "com.google.code.gson:gson:${DependencyVersioning.KotlinDependenciesVersions.gsonVersion}"
    }

    object AndroidDependencies{
        const val appCompatDependency = "androidx.appcompat:appcompat:${DependencyVersioning.AndroidSupportDependenciesVersions.appCompatVersion}"
        const val googleMaterialDependency = "com.google.android.material:material:${DependencyVersioning.AndroidSupportDependenciesVersions.googleMaterialVersion}"
        const val constraintLayoutDependency = "androidx.constraintlayout:constraintlayout:${DependencyVersioning.AndroidSupportDependenciesVersions.constraintLayoutVersion}"
        const val navFragmentDependency = "androidx.navigation:navigation-fragment-ktx:${DependencyVersioning.AndroidSupportDependenciesVersions.navGraphVersion}"
        const val navUiDependency  = "androidx.navigation:navigation-ui-ktx:${DependencyVersioning.AndroidSupportDependenciesVersions.navGraphVersion}"
        const val hiltAndroidDependency = "com.google.dagger:hilt-android:${AppConfig.dagger}"
        const val hiltCompilerDependency = "com.google.dagger:hilt-android-compiler:${AppConfig.dagger}"
        const val lifecycleRuntimeDependency = "androidx.lifecycle:lifecycle-runtime-ktx:${DependencyVersioning.AndroidSupportDependenciesVersions.lifeCycleVersion}"
        const val viewModelDependency = "androidx.lifecycle:lifecycle-viewmodel-ktx:${DependencyVersioning.AndroidSupportDependenciesVersions.lifeCycleVersion}"
        const val viewModelSavedDataDependency = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${DependencyVersioning.AndroidSupportDependenciesVersions.lifeCycleVersion}"
        const val liveDataDependency = "androidx.lifecycle:lifecycle-livedata-ktx:${DependencyVersioning.AndroidSupportDependenciesVersions.lifeCycleVersion}"
        const val coroutinesAndroidDependency = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersioning.AndroidSupportDependenciesVersions.coroutineAndroidVersion}"
        const val dataStorePrefDependency = "androidx.datastore:datastore-preferences:${DependencyVersioning.AndroidSupportDependenciesVersions.dataStorePref}"
    }



    object UnitTestDependencies {
        const val junitDependency =
            "junit:junit:${DependencyVersioning.UnitTestDependenciesVersions.junitVersion}"
        const val androidXJunitExtensionDependency =
            "androidx.test.ext:junit:${DependencyVersioning.UnitTestDependenciesVersions.androidXJunitExtensionVersion}"
        const val androidXEspressoDependency =
            "androidx.test.espresso:espresso-core:${DependencyVersioning.UnitTestDependenciesVersions.androidXEspressoCoreVersion}"
    }

    object ClassPathDependencies{
         const val gradleToolDependency = "com.android.tools.build:gradle:${AppConfig.gradle}"
         const val kotlinDependency = "org.jetbrains.kotlin:kotlin-gradle-plugin:${AppConfig.kotlin}"
         const val daggerHiltDependency = "com.google.dagger:hilt-android-gradle-plugin:${AppConfig.dagger}"
         const val navigationSafeArgsDependency = "androidx.navigation:navigation-safe-args-gradle-plugin:${DependencyVersioning.AndroidSupportDependenciesVersions.navGraphVersion}"
    }


}