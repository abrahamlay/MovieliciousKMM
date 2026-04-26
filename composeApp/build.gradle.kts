import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinKsp)
//    alias(libs.plugins.hiltAndroid)
}

kotlin {
    jvmToolchain(17)
    androidTarget()
    sourceSets {
        val commonMain by getting {
            dependencies {
//                implementation(platform(libs.androidx.compose.bom))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(projects.shared)
//                implementation(libs.landscapist.placeholder)
//                implementation(libs.landscapist.animation)
//                implementation(libs.landscapist.palette)
//                implementation(libs.landscapist.coil3)
//                api(libs.koin.core)
//                api(libs.koin.test)
            }
        }

        val androidMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.koin.android)
                implementation(libs.koin.androidx.compose)
                implementation(libs.lifecycle.runtime.compose)
//                implementation(libs.androidx.material3.android)
//                implementation(libs.hilt.android)
            }
        }
    }
}

// KSP configuration for Room
dependencies {
//    add("kspAndroid", libs.hilt.android.compiler)
//    add("kspIosX64", libs.hilt.android.compiler)
//    add("kspIosArm64", libs.hilt.android.compiler)
//    add("kspIosSimulatorArm64", libs.hilt.android.compiler)
}

android {
    namespace = "org.abrahamlay.movielicious.kmm"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.abrahamlay.movielicious.kmm"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.10"
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
    android.buildFeatures.buildConfig = true
    flavorDimensions += "env"
    productFlavors {
        create("development") {
            dimension = "env"
            applicationId = "org.abrahamlay.movielicious.kmm.dev"
        }

        create("staging") {
            dimension = "env"
            applicationId = "org.abrahamlay.movielicious.kmm.staging"
        }

        create("production") {
            dimension = "env"
            applicationId = "org.abrahamlay.movielicious.kmm"
        }
    }
}

