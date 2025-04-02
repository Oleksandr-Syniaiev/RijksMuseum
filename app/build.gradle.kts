plugins {
    alias(libs.plugins.base.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.base.compose.compiler)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.rijks.museum"

    defaultConfig {
        applicationId = "com.rijks.museum"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    buildFeatures {
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":feature:museum"))
    implementation(project(":domain:museum"))
    implementation(project(":data:museum"))

    implementation(project(":core:utils"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    // Hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.timber)

    detektPlugins(libs.detekt.ruleset.compose)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
