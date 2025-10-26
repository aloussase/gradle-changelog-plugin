package io.github.aloussase.changelog.git

import io.github.aloussase.changelog.ChangelogEntry
import io.github.aloussase.changelog.git.commands.GitCommand

class GitService(
    val currentBranchCommand: GitCommand<Branch>,
    val currentBranchCommitsCommand: GitCommand<List<RawCommit>>,
    val currentReleaseCommand: GitCommand<Tag>,
    val ignoreCommitsContaining: Set<String>
) {

    fun getCurrrentBranchChanges(): ChangelogEntry {
        val currentBranch = currentBranchCommand.execute().getOrThrow()
        val commits = currentBranchCommitsCommand.execute().getOrThrow()
        val release = Tag("Unreleased")
        return ChangelogEntry(
            release.name,
            commits
                .filter { commit ->
                    ignoreCommitsContaining.none {
                        commit.message.contains(it)
                    }
                }
                .map {
                    Commit(
                        it.author,
                        currentBranch.name,
                        it.message
                    )
                }
        )
    }

}
