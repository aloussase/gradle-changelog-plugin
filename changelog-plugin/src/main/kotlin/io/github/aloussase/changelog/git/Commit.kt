package io.github.aloussase.changelog.git

data class Commit(
    val author: String,
    val branchName: String,
    val message: String
)
