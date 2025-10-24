package io.github.aloussase.changelog.formatter

import io.github.aloussase.changelog.Changelog

class MarkdownFormatter : ChangelogFormatter {
    override fun format(changelog: Changelog): String {
        return changelog.entries.first().branchName
    }
}
