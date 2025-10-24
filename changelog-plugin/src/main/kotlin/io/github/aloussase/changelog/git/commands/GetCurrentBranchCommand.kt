package io.github.aloussase.changelog.git.commands

import io.github.aloussase.changelog.git.Branch

class GetCurrentBranchCommand : AbstractGitCommand<Branch>() {
    override fun transform(rawOutput: String): Branch = Branch(rawOutput)

    override val commandLine: String
        get() = "git branch --show-current"
}
