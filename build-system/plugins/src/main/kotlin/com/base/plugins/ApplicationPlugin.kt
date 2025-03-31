@file:Suppress("unused")

package com.base.plugins

import com.base.plugins.extensions.applicationExtension
import com.base.plugins.extensions.applicationPluginId
import com.base.plugins.extensions.configureKotlinAndroidToolchain
import com.base.plugins.extensions.kotlinAndroidPluginId
import com.base.plugins.extensions.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class ApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = applicationPluginId)
        apply(plugin = kotlinAndroidPluginId)

        applicationExtension {
            compileSdk = libs.versions.compileSdk.get().toInt()

            defaultConfig {
                minSdk = libs.versions.minSdk.get().toInt()
                targetSdk = libs.versions.targetSdk.get().toInt()
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
        configureKotlinAndroidToolchain()
    }
}
