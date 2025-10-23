plugins {
    id("buildsrc.convention.kotlin-jvm")
    id("io.github.aloussase.changelog")
    application
}

changelog {
    format = "markdown"
    outputFileName = "CHANGELOG.md"

    git {
        baseBranch = "master"
    }
}

application {
    mainClass = "io.github.aloussase.app.AppKt"
}
