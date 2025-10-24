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

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.hamcrest:java-hamcrest:2.0.0.0")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
