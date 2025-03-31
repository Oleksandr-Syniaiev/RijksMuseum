@file:Suppress("unused")

package com.base.plugins

import com.base.plugins.extensions.kotlinJvmExtension
import com.base.plugins.extensions.kotlinJvmPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class KotlinLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = kotlinJvmPluginId)

        kotlinJvmExtension {
            jvmToolchain(17)
        }
    }
}
