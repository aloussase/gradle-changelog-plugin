package io.github.aloussase.changelog

import io.github.aloussase.changelog.config.ChangelogPluginExtension
import io.github.aloussase.changelog.config.Config
import io.github.aloussase.changelog.formatter.ChangelogFormatterFactory
import io.github.aloussase.changelog.parser.ChangelogParserFactory
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class ChangelogPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("changelog", ChangelogPluginExtension::class.java)

        extension.gitInfo.baseBranch.convention("master")
        extension.format.convention("markdown")
        extension.fileName.convention("CHANGELOG.md")

        project.tasks.register("changelog") {
            val config = Config.from(extension)
            val parser = ChangelogParserFactory.createParser(config.documentFormat)
            val changelogFile = File(config.fileName)
            if (!changelogFile.exists()) {
                changelogFile.createNewFile()
            }
            val document = changelogFile.readText()
            val changelog = parser.parse(document).getOrThrow()

            // TODO: Add commits from current branch here.

            val formatter = ChangelogFormatterFactory.create(config.documentFormat)
            changelogFile.writeText(formatter.format(changelog))
        }
    }
}
