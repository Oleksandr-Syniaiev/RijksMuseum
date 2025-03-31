@file:Suppress("unused")

package com.base.plugins

import com.base.plugins.extensions.configureKotlinAndroidToolchain
import com.base.plugins.extensions.kotlinAndroidPluginId
import com.base.plugins.extensions.libraryExtension
import com.base.plugins.extensions.libraryPluginId
import com.base.plugins.extensions.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class AndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = libraryPluginId)
        apply(plugin = kotlinAndroidPluginId)

        libraryExtension {
            compileSdk = libs.versions.compileSdk.get().toInt()

            defaultConfig {
                minSdk = libs.versions.minSdk.get().toInt()

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

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }


            buildFeatures {
                buildConfig = false
            }
        }
        configureKotlinAndroidToolchain()
    }
}
