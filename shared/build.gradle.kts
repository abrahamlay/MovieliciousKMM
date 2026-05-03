import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version ("2.1.10")
    kotlin("native.cocoapods")
    alias(libs.plugins.sqlDelight)
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("org.abrahamlay.movielicious.kmm")
        }
    }
}

kotlin {
    jvmToolchain(17)
    androidTarget()
    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            freeCompilerArgs += "-Xbinary=bundleId=org.abrahamlay.movielicious.kmm"
            xcf.add(this)
        }
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
                // put your Multiplatform dependencies here
                api(libs.kotlinx.coroutines.core)
                api(libs.ktor.client.core)
                api(libs.ktor.client.logging)
                api(libs.ktor.client.content.negotiation)
                api(libs.ktor.serialization.kotlinx.json)
                api(libs.sqldelight.runtime)

                // those below plugin dependencies also support Kotlin Multiplatform.
                api(libs.landscapist.placeholder)
                api(libs.landscapist.animation)
                api(libs.landscapist.palette)
                api(libs.landscapist.coil3)
                api(libs.koin.core)
                api(libs.koin.test)
//                api(libs.koin.android)
            }
        }


        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting{
            dependencies {
                dependsOn(commonMain)
//                implementation(platform(libs.androidx.compose.bom))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.ktor.client.android)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.sqldelight.android.driver)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.sqldelight.native.driver)
            }
        }
        val iosTest by creating {
            dependsOn(commonTest)
        }


        val iosSimulatorArm64Main by sourceSets.getting
        val iosSimulatorArm64Test by sourceSets.getting

        iosSimulatorArm64Main.dependsOn(iosMain)
        iosSimulatorArm64Test.dependsOn(iosTest)

    }
    cocoapods {
        summary = "Data Domain Movielicious App"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "Shared"
        }
        version = "1.0.0"
    }
}

android {
    namespace = "org.abrahamlay.movielicious.kmm.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.10"
    }
    android.buildFeatures.buildConfig = true
    flavorDimensions += "env"
    productFlavors {
        create("development") {
            dimension = "env"
            buildConfigField("String", "API_BASE_URL", "\"https://api.themoviedb.org/\"")
        }

        create("staging") {
            dimension = "env"
            buildConfigField("String", "API_BASE_URL", "\"https://api.themoviedb.org/\"")
        }

        create("production") {
            dimension = "env"
            buildConfigField("String", "API_BASE_URL", "\"https://api.themoviedb.org/\"")
        }
    }
    compileOptions {
//        isCoreLibraryDesugaringEnabled = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            // Add more exclusions as needed
            excludes += "**/*.kotlin_metadata"
            excludes += "**/*.kotlin_module"
            excludes += "**/module-info.class"
        }
    }
}
