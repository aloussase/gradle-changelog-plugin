package io.github.aloussase.changelog.formatter

import io.github.aloussase.changelog.formats.DocumentFormat

abstract class ChangelogFormatterFactory {

    companion object {
        fun create(format: DocumentFormat): ChangelogFormatter {
            return when (format) {
                DocumentFormat.Markdown -> MarkdownFormatter()
            }
        }
    }
}
