package io.github.aloussase.changelog

import io.github.aloussase.changelog.config.ChangelogPluginExtension
import io.github.aloussase.changelog.config.Config
import org.gradle.api.Plugin
import org.gradle.api.Project

class ChangelogPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("changelog", ChangelogPluginExtension::class.java)

        extension.gitInfo.baseBranch.convention("master")
        extension.format.convention("markdown")

        project.tasks.register("changelog") {
            val config = Config.from(extension)
            println(config)
        }
    }
}
