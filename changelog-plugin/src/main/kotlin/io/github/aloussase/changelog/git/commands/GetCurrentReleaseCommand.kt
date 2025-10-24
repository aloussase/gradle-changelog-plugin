package io.github.aloussase.changelog.git.commands

import io.github.aloussase.changelog.git.Tag

class GetCurrentReleaseCommand : AbstractGitCommand<Tag>() {
    override val commandLine: String
        get() = "git tag | tail -n1"

    override fun transform(rawOutput: String): Tag = Tag(rawOutput)
}
