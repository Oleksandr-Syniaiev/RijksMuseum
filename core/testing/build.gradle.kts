plugins {
    alias(libs.plugins.base.library.android)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.rijks.museum.core.testing"

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
    implementation(project(":domain:museum"))
    implementation(project(":data:museum"))

    api(libs.kotlin.coroutines.test)
    api(libs.junit)
    api(libs.mockito.kotlin)
    api(libs.test.mockk)
}
