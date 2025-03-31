plugins {
    alias(libs.plugins.base.library.android)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.base.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.rijks.museum.core.ui"

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
    api(platform(libs.compose.bom))

    api(libs.activity.compose)
    api(libs.navigation.compose)
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling.preview)
    api(libs.material)
    api(libs.compose.material3)
    api(libs.compose.material)
    api(libs.lifecycle.runtime.ktx)
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.accompanist.compose.placeholder)

    implementation(libs.kotlinx.serialization.json)

    detektPlugins(libs.detekt.ruleset.compose)

    debugImplementation(libs.ui.tooling)
}
