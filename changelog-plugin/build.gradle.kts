plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "2.1.21"
}

group = "io.github.aloussase"

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("changelog") {
            id = "$group.$name"
            implementationClass = "$group.$name.ChangelogPlugin"
        }
    }
}
