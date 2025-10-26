package io.github.aloussase.changelog

import io.github.aloussase.changelog.config.ChangelogPluginExtension
import io.github.aloussase.changelog.config.Config
import org.gradle.api.Plugin
import org.gradle.api.Project

class ChangelogPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("changelog", ChangelogPluginExtension::class.java)

        extension.gitInfo.baseBranch.convention("master")
        extension.gitInfo.ignoreCommits.convention(emptyList())
        extension.format.convention("markdown")
        extension.fileName.convention("CHANGELOG.md")

        project.tasks.register("changelog", ChangelogTask::class.java) {
            it.config = Config.configure(extension)
        }
    }
}
