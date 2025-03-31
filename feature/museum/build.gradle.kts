plugins {
    alias(libs.plugins.base.library.android)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.base.compose.compiler)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.rijks.museum.arts"

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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:utils"))
    implementation(project(":domain:museum"))

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.play.services.location)

    implementation(libs.kotlinx.serialization.json)

    // Hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // Testing
    testImplementation(project(":core:testing"))

    detektPlugins(libs.detekt.ruleset.compose)
}
