package com.base.plugins.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

internal val Project.libs
    get() = the<LibrariesForLibs>()

internal fun Project.libraryExtension(action: LibraryExtension.() -> Unit) =
    extensions.configure<LibraryExtension>(action)

internal fun Project.applicationExtension(action: ApplicationExtension.() -> Unit) =
    extensions.configure<ApplicationExtension>(action)

internal fun Project.configureKotlinAndroidToolchain() {
    extensions.configure<KotlinAndroidProjectExtension> {
        jvmToolchain(17)
    }
}

internal fun Project.kotlinJvmExtension(action: KotlinJvmProjectExtension.() -> Unit) =
    extensions.configure<KotlinJvmProjectExtension>(action)

internal fun Project.findBooleanProperty(propertyName: String): Boolean =
   findProperty(propertyName).toString().toBoolean()
