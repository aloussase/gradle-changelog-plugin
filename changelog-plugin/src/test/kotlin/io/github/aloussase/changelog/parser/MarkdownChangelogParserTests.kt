package io.github.aloussase.changelog.parser

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MarkdownChangelogParserTests {

    @Test
    fun givenEmptyFileWhenParseIsInvokedThenReturnEmptyChangelog() {
        val input = ""
        val parser = MarkdownChangelogParser()

        val result = parser.parse(input)

        assertThat(result.isSuccess, equalTo(true))
        assertThat(result.getOrThrow().entries, emptyIterable())
    }

    @ParameterizedTest
    @ValueSource(strings = ["# Changelog", "# CHANGELOG"])
    fun givenDocumentContainingOnlyHeaderWhenParseIsInvokedThenReturnEmptyChangelog(doc: String) {
        val parser = MarkdownChangelogParser()

        val result = parser.parse(doc)

        assertThat(result.isSuccess, equalTo(true))
        assertThat(result.getOrThrow().entries, emptyIterable())
    }

    @Test
    fun givenDocumentContainingSingleEntryWithNoCommitsWhenParseisInvokedThenReturnChangelogWithThatEntryAndNoCommits() {
        val doc = "# Changelog\n\n## [0.1.1]"
        val parser = MarkdownChangelogParser()

        val result = parser.parse(doc)

        assertThat(result.isSuccess, equalTo(true))
        assertThat(result.getOrThrow().entries, hasSize(1))
        assertThat(result.getOrThrow().entries.first().release, equalTo("0.1.1"))
        assertThat(result.getOrThrow().entries.first().commits, emptyIterable())
    }

    @Test
    fun givenDocumentContainingSingleEntryWithCommitsWhenParseIsInvokedThenReturnChangelogWithSingleEntryAndCommits() {
        val doc =
            "# Changelog\n\n## [0.1.1]\n- LOYMAR-123: first commit (johndoe@gmail.com)\n- LOYMAR-123: second commit (johndoe@gmail.com)"
        val parser = MarkdownChangelogParser()

        val result = parser.parse(doc)

        assertThat(result.isSuccess, equalTo(true))
        assertThat(result.getOrThrow().entries, hasSize(1))
        assertThat(result.getOrThrow().entries[0].commits, hasSize(2))
        assertThat(result.getOrThrow().entries[0].commits[0].message, equalTo("first commit"))
        assertThat(result.getOrThrow().entries[0].commits[1].message, equalTo("second commit"))
    }

    @Test
    fun givenDocumentWithTwoEntriesWhenParseIsInvokedThenReturnChangelogWithTwoEntries() {
        val doc =
            "# Changelog\n\n## [0.1.1]\n- LOYMAR-123: first commit (johndoe@gmail.com)\n\n## [0.1.0]\n- LOYMAR-123: third commit (janedoe@gmail.com)"
        val parser = MarkdownChangelogParser()

        val result = parser.parse(doc)

        assertThat(result.isSuccess, equalTo(true))
        assertThat(result.getOrThrow().entries, hasSize(2))
        assertThat(result.getOrThrow().entries[0].commits, hasSize(1))
        assertThat(result.getOrThrow().entries[1].commits, hasSize(1))
        assertThat(result.getOrThrow().entries[1].commits[0].message, equalTo("third commit"))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "LOYMAR-123",
            "0.1.x",
            ""
        ]
    )
    fun givenDocumentWithInvalidReleaseVersionWhenParseIsInvokedThenExceptionShouldBeThrown(releaseVersion: String) {
        val doc =
            "# Changelog\n\n## $releaseVersion\n- LOYMAR-123: first commit (johndoe@gmail.com)\n\n## [0.1.0]\n- LOYMAR-123: third commit (janedoe@gmail.com)"
        val parser = MarkdownChangelogParser()

        val result = parser.parse(doc)

        assertThat(result.isFailure, equalTo(true))
        assertThat(result.exceptionOrNull()?.message, containsString("Expected a valid release version"))
        assertThat(result.exceptionOrNull()?.message, containsString(releaseVersion))
    }

    @ParameterizedTest
    @ValueSource(strings = ["LOYMAR 123", "", "@@@"])
    fun givenDocumentWithInvalidCommitBranchWhenParseIsInvokedThenFailureShouldBeReturned(branchName: String) {
        val doc = "# Changelog\n\n## [1.1.1]\n- $branchName: first commit (johndoe@gmail.com)"
        val parser = MarkdownChangelogParser()

        val result = parser.parse(doc)

        assertThat(result.isFailure, equalTo(true))
        assertThat(result.exceptionOrNull()?.message, containsString(branchName))
        assertThat(result.exceptionOrNull()?.message, containsString("Expected a valid commit"))
    }

    @Test
    fun givenDocumentWithReleaseNameUnreleasedWhenParseIsCalledThenDocumentIsParsedCorrectly() {
        val doc = "# Changelog\n\n## [Unreleased]\n- LOYMAR-123: first commit (johndoe@gmail.com)"
        val parser = MarkdownChangelogParser()

        val result = parser.parse(doc)

        assertThat(result.isSuccess, equalTo(true))
        assertThat(result.getOrThrow().entries.first().release, equalTo("Unreleased"))
    }

}
