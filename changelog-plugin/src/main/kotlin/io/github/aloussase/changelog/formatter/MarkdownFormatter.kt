package io.github.aloussase.changelog.formatter

import io.github.aloussase.changelog.Changelog

class MarkdownFormatter : ChangelogFormatter {
    override fun format(changelog: Changelog): String {
        return buildString {
            append("# CHANGELOG\n\n")
            for ((entryIndex, entry) in changelog.entries.sorted().withIndex()) {
                append("## [${entry.release}]")
                for ((index, commit) in entry.commits.withIndex()) {
                    if (index == 0) {
                        append("\n")
                    }

                    append("- ${commit.branchName}: ${commit.message} (${commit.author})")

                    if (index < entry.commits.size - 1) {
                        append("\n")
                    }
                }

                if (entryIndex < changelog.entries.size - 1) {
                    append("\n\n")
                }
            }
        }
    }
}
