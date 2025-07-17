plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinCocoapods)
    id("maven-publish")
}

group = "com.inspiringapps.libs"
version = "1.1"

publishing {
    repositories {
        maven {
            name = "mavenLocal"
        }
    }
}

kotlin {
    androidLibrary {
        namespace = "com.inspiringapps.mapboxtest.library"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withHostTestBuilder {}

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    cocoapods {
        homepage = "MapboxMapView Library"
        summary = "Crossplatform MapBox Map View Library"
        version = "1.0"
        ios.deploymentTarget = "16.0"g

        framework {
            baseName = "composeApp"
//            isStatic = true
//            transitiveExport = true
        }

        pod("MapboxWrapper") {
            version = "0.1.0"
            source = path(project.file("./MapboxWrapper"))
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
//
//
//        pod("MapboxMaps") {
//            version = "11.13.3"
//            extraOpts += listOf("-compiler-option", "-fmodules")
//        }
//
//        pod("MapboxCoreMaps") {
//            extraOpts += listOf("-compiler-option", "-fmodules")
//        }
//
//        pod("MapboxCommon") {
//            extraOpts += listOf("-compiler-option", "-fmodules")
//        }
    }

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate
//    val xcfName = "libraryKit"

    iosX64 {
//        binaries.framework {
//            baseName = xcfName
//        }
    }

    iosArm64 {
//        binaries.framework {
//            baseName = xcfName
//        }
    }

    iosSimulatorArm64 {
//        binaries.framework {
//            baseName = xcfName
//        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(compose.preview)
                implementation(libs.mapbox.android)
                implementation(libs.mapbox.compose)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.testExt.junit)
            }
        }

        iosMain {
            dependencies {}
        }
    }
}
