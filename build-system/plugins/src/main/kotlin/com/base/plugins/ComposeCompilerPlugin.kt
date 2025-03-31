package com.base.plugins

import com.base.plugins.extensions.applicationExtension
import com.base.plugins.extensions.applicationPluginId
import com.base.plugins.extensions.composeCompilerId
import com.base.plugins.extensions.findBooleanProperty
import com.base.plugins.extensions.implementation
import com.base.plugins.extensions.libraryExtension
import com.base.plugins.extensions.libraryPluginId
import com.base.plugins.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class ComposeCompilerPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = composeCompilerId)

        if (findBooleanProperty("app.compose.compiler.report.enabled")) {
            apply(from = "${rootProject.projectDir}/build-system/plugins/compose-compiler.gradle")
        }

        pluginManager.withPlugin(applicationPluginId) {
            applicationExtension {
                buildFeatures {
                    compose = true
                }
                dependencies {
                    implementation(platform(libs.compose.bom))
                }
            }
        }

        pluginManager.withPlugin(libraryPluginId) {
            libraryExtension {
                buildFeatures {
                    compose = true
                }
                dependencies {
                    implementation(platform(libs.compose.bom))
                }
            }
        }
    }
}
