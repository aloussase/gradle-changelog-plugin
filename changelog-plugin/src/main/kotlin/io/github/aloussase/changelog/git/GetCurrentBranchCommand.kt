package io.github.aloussase.changelog.git

class GetCurrentBranchCommand : AbstractGitCommand<String>() {
    override fun transform(rawOutput: String): String = rawOutput

    override val commandLine: String
        get() = "git branch --show-current"
}
