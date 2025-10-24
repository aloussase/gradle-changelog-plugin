package io.github.aloussase.changelog.parser

import io.github.aloussase.changelog.Changelog
import io.github.aloussase.changelog.ChangelogEntry
import io.github.aloussase.changelog.git.Commit
import org.gradle.api.GradleException

/**
 * A ChangelogParser that assumes it's input to be a Markdown document.
 */
class MarkdownChangelogParser : ChangelogParser {

    companion object {
        private val RELEASE_VERSION_REGEX = Regex("## \\[(\\d+\\.\\d+\\.\\d+)]")
        private val COMMIT_REGEX = Regex("- ([\\w-]+): ([\\w ]+) \\(([\\w.]+@[\\w.]+)\\)")
        private val DOC_TITLE_REGEX = Regex("# (Changelog|CHANGELOG)")
    }

    override fun parse(doc: String): Result<Changelog> {
        if (doc.isEmpty()) {
            return Result.success(Changelog())
        }

        val entries = arrayListOf<ChangelogEntry>()

        for (blk in doc.split("\n\n")) {
            if (DOC_TITLE_REGEX.matches(blk)) continue

            val lines = blk.split("\n")
            if (lines.isEmpty()) continue

            val releaseMatch = RELEASE_VERSION_REGEX.matchEntire(lines[0])
            if (releaseMatch == null) {
                return Result.failure(
                    GradleException("Expected a valid release version, but got ${lines[0]}")
                )
            }

            val releaseVersion = releaseMatch.groupValues[1]
            val commits = arrayListOf<Commit>()

            for (line in lines.subList(1, lines.size)) {
                val commitMatch = COMMIT_REGEX.matchEntire(line)
                if (commitMatch != null) {
                    val (branchName, message, author) = commitMatch.destructured
                    commits.add(
                        Commit(
                            author,
                            branchName,
                            message,
                        )
                    )
                } else {
                    return Result.failure(
                        GradleException("Expected a valid commit line, but got $line")
                    )
                }
            }

            entries.add(
                ChangelogEntry(
                    releaseVersion,
                    commits
                )
            )
        }

        return Result.success(Changelog(entries))
    }
}
