package io.github.aloussase.changelog.git

class GetCurrentReleaseCommand : AbstractGitCommand() {
    override val commandLine: String
        get() = "git tag | tail -n1"
}
