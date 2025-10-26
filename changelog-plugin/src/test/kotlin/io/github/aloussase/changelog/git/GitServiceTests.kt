package io.github.aloussase.changelog.git

import io.github.aloussase.changelog.git.commands.GitCommand
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GitServiceTests {

    val currentBranchCommand = mock(GitCommand::class.java) as GitCommand<Branch>
    val currentBranchCommitsCommand = mock(GitCommand::class.java) as GitCommand<List<RawCommit>>
    val currentReleaseCommand = mock(GitCommand::class.java) as GitCommand<Tag>

    @Test
    fun givenListOfCommitsToIgnoreWhenGettingCurrentBranchChangesThenCommitsContainingMessagesMatchingTheCriteriaAreNotIncludedInTheResults() {
        val commitsToIgnore = setOf("test", "apple")

        `when`(currentBranchCommand.execute())
            .thenReturn(Result.success(Branch("master")))

        `when`(currentBranchCommitsCommand.execute())
            .thenReturn(
                Result.success(
                    listOf(
                        RawCommit("johndoe@email.com", "first commit"),
                        RawCommit("johndoe@email.com", "test: add test for thing"),
                        RawCommit("johndoe@email.com", "apples now cost more than bananas"),
                        RawCommit("johndoe@email.com", "upgrade the tests"),
                    )
                )
            )

        val gitService = GitService(
            currentBranchCommand,
            currentBranchCommitsCommand,
            currentReleaseCommand,
            commitsToIgnore
        )

        val result = gitService.getCurrrentBranchChanges()

        assertThat(result.commits, hasSize(1))
        assertThat(
            result.commits.map { it.message },
            contains("first commit")
        )
    }

    @Test
    fun givenEmptyListOfCommitsToIgnoreWhenCurrentBranchChangesThenAllCommitsAreReturned() {
        val commitsToIgnore = setOf<String>()

        `when`(currentBranchCommand.execute())
            .thenReturn(Result.success(Branch("master")))

        `when`(currentBranchCommitsCommand.execute())
            .thenReturn(
                Result.success(
                    listOf(
                        RawCommit("johndoe@email.com", "first commit"),
                        RawCommit("johndoe@email.com", "test: add test for thing"),
                        RawCommit("johndoe@email.com", "apples now cost more than bananas"),
                        RawCommit("johndoe@email.com", "upgrade the tests"),
                    )
                )
            )

        val gitService = GitService(
            currentBranchCommand,
            currentBranchCommitsCommand,
            currentReleaseCommand,
            commitsToIgnore
        )

        val result = gitService.getCurrrentBranchChanges()

        assertThat(result.commits, hasSize(4))
        assertThat(
            result.commits.map { it.message },
            contains(
                "first commit",
                "test: add test for thing",
                "apples now cost more than bananas",
                "upgrade the tests"
            )
        )
    }

}
