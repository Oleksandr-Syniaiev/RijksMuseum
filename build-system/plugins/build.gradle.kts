plugins {
    `kotlin-dsl`
}
group = "com.rijks.museum"

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(files((libs as Any).javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.kotlinx.serialization.json)
}
gradlePlugin {
    plugins {

        register("base.library.kotlin") {
            id = "base.library.kotlin"
            implementationClass = "com.base.plugins.KotlinLibraryPlugin"
        }

        register("base.library.android") {
            id = "base.library.android"
            implementationClass = "com.base.plugins.AndroidLibraryPlugin"
        }

        register("base.application") {
            id = "base.application"
            implementationClass = "com.base.plugins.ApplicationPlugin"
        }

        register("base.compose.compiler") {
            id = "base.compose.compiler"
            implementationClass = "com.base.plugins.ComposeCompilerPlugin"
        }
    }
}
