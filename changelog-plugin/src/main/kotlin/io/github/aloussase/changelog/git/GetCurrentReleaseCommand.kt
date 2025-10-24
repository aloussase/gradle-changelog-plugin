package io.github.aloussase.changelog.git

class GetCurrentReleaseCommand : AbstractGitCommand<String>() {
    override val commandLine: String
        get() = "git tag | tail -n1"

    override fun transform(rawOutput: String): String = rawOutput
}
