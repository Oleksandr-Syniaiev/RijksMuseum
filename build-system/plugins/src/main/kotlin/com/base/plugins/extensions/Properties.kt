package com.base.plugins.extensions

import org.gradle.api.Project
import java.io.File
import java.util.Properties

val Project.museumApiKey: String
    get() = loadProperties().getProperty("MUSEUM_API_KEY")

val Project.buildVersionCode: Int
    get() = loadProperties(
        File("${resourcesDir}/version/version.properties")
    )
        .getProperty("VERSION_CODE")
        .toInt()

val Project.buildVersionName: String
    get() = getVersionName(
        File("${resourcesDir}/version/version.properties")
    )


val Project.resourcesDir: String
    get() = "${project.rootDir}/build-system/plugins/src/main/resources"

private fun getVersionName(file: File): String {
    val properties = loadProperties(file)
    val major = properties.getProperty("MAJOR")
    val minor = properties.getProperty("MINOR")
    val patch = properties.getProperty("PATCH")
    val build = properties.getProperty("BUILD")
    return "$major.$minor.$patch.$build"
}

fun loadProperties(file: File): Properties {
    val properties = Properties()
    if (file.exists()) {
        file.inputStream().use { properties.load(it) }
    }
    return properties
}

fun Project.loadProperties(): Properties {
    val pathToPropertiesFolder = "${resourcesDir}/credentials"
    val pathToProperties = "$pathToPropertiesFolder/api_keys.properties"
    val propertiesFile = File(pathToProperties)
    return loadProperties(propertiesFile)
}
