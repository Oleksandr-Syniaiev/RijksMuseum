pluginManagement {
    includeBuild("build-system")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Rijks Museum"
include(":app")
include(":core:ui")
include(":feature:museum")
include(":core:utils")
include(":core:testing")
include(":data:museum")
include(":domain:museum")
