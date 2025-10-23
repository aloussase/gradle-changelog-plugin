package io.github.aloussase.changelog.config

import org.gradle.api.Action
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Nested

abstract class ChangelogPluginExtension {
    @get:Nested
    abstract val gitInfo: GitInfo;

    abstract val format: Property<String>

    abstract val outputFileName: Property<String>

    fun git(action: Action<GitInfo>) = action.execute(gitInfo)
}
