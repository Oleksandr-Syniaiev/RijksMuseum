import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.gradle.ktlint) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.detekt.core)
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose.compiler)
}

// Detekt plugin configuration
allprojects {
    apply(plugin = rootProject.libs.plugins.detekt.core.get().pluginId)

    detekt {
        buildUponDefaultConfig = true // preconfigure defaults

        /*
        Point to your custom config defining rules to run, overwriting default behavior.
        Every detekt version has changes to the default config, so to ensure a stable behavior
        it is recommended to use a custom config.
         */
        config.setFrom("$rootDir/config/detekt.yml")
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            html.required.set(true) // observe findings in your browser with structure and code snippets
            xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
            txt.required.set(false) // similar to the console output, contains issue signature to manually edit baseline files
            sarif.required.set(false) // standardized SARIF format (https://sarifweb.azurewebsites.net/)
            md.required.set(false) // simple Markdown format
        }
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = "1.8"
    }
    tasks.withType<DetektCreateBaselineTask>().configureEach {
        jvmTarget = "1.8"
    }
}
composeCompiler {
    // Needed for Layout Inspector to be able to see all of the nodes in the component tree
    includeSourceInformation = true
}

// Ktlint plugin configuration
allprojects {
    apply(plugin = rootProject.libs.plugins.gradle.ktlint.get().pluginId)

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set(rootProject.libs.versions.ktlint.ruleset.standart.get())
    }
}
