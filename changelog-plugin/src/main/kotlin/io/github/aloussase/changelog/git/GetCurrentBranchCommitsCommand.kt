package io.github.aloussase.changelog.git

class GetCurrentBranchCommitsCommand : AbstractGitCommand<List<Pair<String, String>>>() {
    override val commandLine: String
        get() = "git log main..HEAD --no-merges --oneline --pretty=format:\"%an|%s\""

    override fun transform(rawOutput: String): List<Pair<String, String>> =
        rawOutput
            .split("\n")
            .map { it.split("|") }
            .map { Pair(it[0], it[1]) }
}
