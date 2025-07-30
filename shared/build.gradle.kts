import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    
    // Temporarily disabled iOS targets for Android development
    // listOf(
    //     iosX64(),
    //     iosArm64(),
    //     iosSimulatorArm64()
    // ).forEach {
    //     it.binaries.framework {
    //         baseName = "shared"
    //         isStatic = true
    //     }
    // }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            implementation(libs.retrofit)
            implementation(libs.retrofit.converter.gson)
            implementation(libs.okhttp)
            implementation(libs.okhttp.logging)
        }
        // Temporarily disabled iOS dependencies
        // iosMain.dependencies {
        //     implementation("io.ktor:ktor-client-darwin:2.3.7")
        //     implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
        //     implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
        //     implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
        // }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.lawbot"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
