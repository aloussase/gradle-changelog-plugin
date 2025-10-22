package io.github.aloussase.changelog.parser

import io.github.aloussase.changelog.Changelog

/**
 * A ChangelogParser that assumes it's input to be a Markdown document.
 */
class MarkdownChangelogParser : ChangelogParser {
    override fun parse(doc: String): Result<Changelog> {
        TODO("Not yet implemented")
    }
}
