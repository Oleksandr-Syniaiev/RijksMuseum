plugins {
    alias(libs.plugins.base.library.android)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.parcelable)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.rijks.museum.domain"

    defaultConfig {
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
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(project(":core:utils"))

    // Hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)

    // Testing
    testImplementation(project(":core:testing"))
}
