// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

allprojects {
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)

    detekt {
        allRules = true
        config.setFrom(files("$rootDir/config/detekt/detekt-config.yml"))
        baseline = file("detekt-baseline.xml")
        buildUponDefaultConfig = true
    }
}

task<Delete>("clean") {
    delete = setOf(rootProject.layout.buildDirectory)
}
