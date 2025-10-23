package io.github.aloussase.changelog.config

import org.gradle.api.provider.Property

interface GitInfo {

    val baseBranch: Property<String>

}
