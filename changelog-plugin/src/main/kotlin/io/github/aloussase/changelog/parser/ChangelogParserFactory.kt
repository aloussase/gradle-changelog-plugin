package io.github.aloussase.changelog.parser

import io.github.aloussase.changelog.formats.DocumentFormat

abstract class ChangelogParserFactory {
    companion object {
        fun createParser(documentFormat: DocumentFormat): ChangelogParser {
            return when (documentFormat) {
                DocumentFormat.Markdown -> MarkdownChangelogParser()
            }
        }
    }
}
