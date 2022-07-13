// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
            url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        }
    }
    dependencies {
        classpath(AppDependencies.ClassPathDependencies.gradleToolDependency)
        classpath(AppDependencies.ClassPathDependencies.kotlinDependency)
        classpath(AppDependencies.ClassPathDependencies.daggerHiltDependency)
        classpath(AppDependencies.ClassPathDependencies.navigationSafeArgsDependency)
    }
}
plugins {
    id("com.android.application") version "7.1.2" apply false
    id("com.android.library") version "7.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.6.20" apply false
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}