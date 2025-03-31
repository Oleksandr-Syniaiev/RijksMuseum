package com.base.plugins.extensions

import org.gradle.api.Project
import java.io.File
import java.util.Properties

val Project.museumApiKey: String
    get() = loadProperties().getProperty("MUSEUM_API_KEY")


fun loadProperties(file: File): Properties {
    val properties = Properties()
    if (file.exists()) {
        file.inputStream().use { properties.load(it) }
    }
    return properties
}

fun Project.loadProperties(): Properties {
    val pathToPropertiesFolder = "${project.rootDir}/build-system/plugins/src/main/resources/credentials"
    val pathToProperties = "$pathToPropertiesFolder/api_keys.properties"
    val propertiesFile = File(pathToProperties)
    return loadProperties(propertiesFile)
}
