import com.base.plugins.extensions.museumApiKey

plugins {
    alias(libs.plugins.base.library.android)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.rijks.museum.data"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "API_KEY", museumApiKey)
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
    implementation(project(":domain:museum"))
    implementation(project(":core:utils"))

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.play.services.location)

    // Hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3.logging.interceptor)

    // Testing
    testImplementation(project(":core:testing"))
}
