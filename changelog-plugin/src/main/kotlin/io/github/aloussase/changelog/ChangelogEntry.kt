package io.github.aloussase.changelog

import io.github.aloussase.changelog.git.Commit

data class ChangelogEntry(
    val branchName: String,
    val commits: List<Commit> = emptyList(),
)
