plugins {
    id("buildsrc.convention.kotlin-jvm")
    id("io.github.aloussase.changelog")
    application
}

changelog {
    format = "markdown"
    fileName = "CHANGELOG.md"

    git {
        baseBranch = "main"
    }
}

application {
    mainClass = "io.github.aloussase.app.AppKt"
}
