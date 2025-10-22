dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

pluginManagement {
    includeBuild("./changelog-plugin")
}

include(":app")

rootProject.name = "changelog"
