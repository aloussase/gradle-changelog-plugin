package io.github.aloussase.changelog

import org.gradle.api.Plugin
import org.gradle.api.Project

class ChangelogPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("changelog") {
        }
    }
}
