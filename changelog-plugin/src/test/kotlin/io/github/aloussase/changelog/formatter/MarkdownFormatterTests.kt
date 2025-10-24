package io.github.aloussase.changelog.formatter

import io.github.aloussase.changelog.Changelog
import io.github.aloussase.changelog.ChangelogEntry
import io.github.aloussase.changelog.git.Commit
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class MarkdownFormatterTests {

    @Test
    fun givenChangelogWhenFormatIsInvokedThenADocumentWithTheRightFormatIsGenerated() {
        val changelog = Changelog(
            listOf(
                ChangelogEntry(
                    "0.1.0",
                    listOf(
                        Commit(
                            "johndoe@email.com",
                            "LOYMAR-123",
                            "changes nice"
                        ),
                    ),
                ),
                ChangelogEntry(
                    "0.1.1",
                    listOf()
                ),
            ),
        )

        val expectedDocument =
            "# CHANGELOG\n\n## [0.1.1]\n\n## [0.1.0]\n- LOYMAR-123: changes nice (johndoe@email.com)"
        val formatter = MarkdownFormatter()

        val result = formatter.format(changelog)

        assertThat(result, equalTo(expectedDocument))
    }

    @Test
    fun givenChangeloWithEntriesOutOfOrderWhenFormatIsCalledThenDocumentWithEntriesInRightOrderIsGenerated() {
        val changelog = Changelog(
            listOf(
                ChangelogEntry("0.1.0", listOf()),
                ChangelogEntry("0.1.1", listOf()),
                ChangelogEntry("2.0.0", listOf()),
                ChangelogEntry("2.1.0", listOf()),
                ChangelogEntry("2.0.2", listOf()),
                ChangelogEntry("2.0.1", listOf()),
            ),
        )

        val expectedDocument =
            "# CHANGELOG\n\n## [2.1.0]\n\n## [2.0.2]\n\n## [2.0.1]\n\n## [2.0.0]\n\n## [0.1.1]\n\n## [0.1.0]"
        val formatter = MarkdownFormatter()

        val result = formatter.format(changelog)

        assertThat(result, equalTo(expectedDocument))
    }

}
