package io.github.aloussase.changelog.git.commands

import io.github.aloussase.changelog.git.RawCommit

class GetCurrentBranchCommitsCommand : AbstractGitCommand<List<RawCommit>>() {
    override val commandLine: String
        get() = "git log main..HEAD --no-merges --oneline --pretty=format:\"%an|%s\""

    override fun transform(rawOutput: String): List<RawCommit> =
        rawOutput
            .split("\n")
            .map { it.split("|") }
            .map { RawCommit(it[0], it[1]) }
}
