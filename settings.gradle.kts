enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "PlaceholderAPI-Expansion"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.6.0"
}

arrayOf("paper", "velocity").forEach {
    include("placeholderapi-expansion-$it")

    project(":placeholderapi-expansion-$it").projectDir = file(it)
}

