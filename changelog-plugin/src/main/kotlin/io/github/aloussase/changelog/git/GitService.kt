package io.github.aloussase.changelog.git

import io.github.aloussase.changelog.ChangelogEntry
import io.github.aloussase.changelog.git.commands.GitCommand

class GitService(
    val currentBranchCommand: GitCommand<Branch>,
    val currentBranchCommitsCommand: GitCommand<List<RawCommit>>,
    val currentReleaseCommand: GitCommand<Tag>
) {

    fun getCurrrentBranchChanges(): ChangelogEntry {
        val currentBranch = currentBranchCommand.execute()
        val commits = currentBranchCommitsCommand.execute()
        val release = Tag("Unreleased")
        return ChangelogEntry(
            release.name,
            commits.map {
                Commit(
                    it.author,
                    currentBranch.name,
                    it.message
                )
            }
        )
    }

}
