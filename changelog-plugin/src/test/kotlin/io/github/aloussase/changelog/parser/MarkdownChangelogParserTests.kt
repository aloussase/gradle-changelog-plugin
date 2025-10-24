package io.github.aloussase.changelog.parser

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.emptyIterable
import org.hamcrest.Matchers.equalTo
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
        val doc = "# Changelog\n\n## LOYMAR-123"
        val parser = MarkdownChangelogParser()

        val result = parser.parse(doc)

        assertThat(result.isSuccess, equalTo(true))
        assertThat(result.getOrThrow().entries, hasSize(1))
        assertThat(result.getOrThrow().entries.first().branchName, equalTo("LOYMAR-123"))
        assertThat(result.getOrThrow().entries.first().commits, emptyIterable())
    }

    @Test
    fun givenDocumentContainingSingleEntryWithCommitsWhenParseIsInvokedThenReturnChangelogWithSingleEntryAndCommits() {
        val doc = "# Changelog\n\n## LOYMAR-123\n- first commit\n- second commit"
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
        val doc = "# Changelog\n\n## LOYMAR-123\n- first commit\n- second commit\n\n## LOYMAR-456\n- third commit"
        val parser = MarkdownChangelogParser()

        val result = parser.parse(doc)

        assertThat(result.isSuccess, equalTo(true))
        assertThat(result.getOrThrow().entries, hasSize(2))
        assertThat(result.getOrThrow().entries[0].commits, hasSize(2))
        assertThat(result.getOrThrow().entries[1].commits, hasSize(1))
        assertThat(result.getOrThrow().entries[1].commits[0].message, equalTo("third commit"))
    }

}
