package io.github.aloussase.changelog.config

import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

interface GitInfo {

    /**
     * What is the branch we are comparing against.
     */
    val baseBranch: Property<String>

    /**
     * Ignore the commits containing these strings.
     */
    val ignoreCommits: ListProperty<String>

}
