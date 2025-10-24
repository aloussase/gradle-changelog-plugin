package io.github.aloussase.changelog

import io.github.aloussase.changelog.formatter.ChangelogFormatter
import io.github.aloussase.changelog.formatter.MarkdownFormatter
import io.github.aloussase.changelog.parser.ChangelogParser
import io.github.aloussase.changelog.parser.MarkdownChangelogParser
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ChangelogPluginTest {

    @ParameterizedTest
    @MethodSource("provideParserAndFormatter")
    fun givenParserAndFormatterWhenComposingThemThenReturnTheOriginalInput(
        parser: ChangelogParser,
        formatter: ChangelogFormatter,
        input: String
    ) {
        assertThat(
            formatter.format(
                parser.parse(
                    input
                ).getOrThrow()
            ),
            equalTo(input)
        )
    }

    companion object {
        @JvmStatic
        fun provideParserAndFormatter(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    MarkdownChangelogParser(),
                    MarkdownFormatter(),
                    "# CHANGELOG\n\n## [0.1.2]\n- feat-01: commit message (johndoe@email.com)"
                )
            )
        }
    }

}
