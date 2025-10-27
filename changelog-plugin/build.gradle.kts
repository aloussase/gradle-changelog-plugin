plugins {
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.2.1"
    signing
    id("com.gradleup.shadow") version "9.2.2"
    kotlin("jvm") version "2.1.21"
}

group = "io.github.aloussase"
version = "0.1.0"

repositories {
    mavenCentral()
}



gradlePlugin {
    website = "https://github.com/aloussase/gradle-changelog-plugin"
    vcsUrl = "https://github.com/aloussase/gradle-changelog-plugin.git"

    plugins {
        create("changelog") {
            id = "$group.$name"
            displayName = "Plugin for generating CHANGELOG entries"
            description = "Generate a CHANGELOG automatically from Git commit history"
            tags = listOf("git", "changelog", "documentation", "tool")
            implementationClass = "$group.$name.ChangelogPlugin"
        }
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.hamcrest:java-hamcrest:2.0.0.0")
    testImplementation("org.mockito:mockito-core:5.+")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<Jar>().configureEach {
    archiveClassifier.set("")
}
