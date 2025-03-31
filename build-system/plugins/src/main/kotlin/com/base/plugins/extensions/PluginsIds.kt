package com.base.plugins.extensions

import org.gradle.api.Project

internal val Project.applicationPluginId
    get() = libs.plugins.com.android.application.get().pluginId

internal val Project.libraryPluginId
    get() = libs.plugins.com.android.library.get().pluginId

internal val Project.kotlinAndroidPluginId
    get() = libs.plugins.org.jetbrains.kotlin.android.get().pluginId

internal val Project.composeCompilerId
    get() = libs.plugins.compose.compiler.get().pluginId

internal val Project.kotlinJvmPluginId
    get() = libs.plugins.org.jetbrains.kotlin.jvm.get().pluginId

