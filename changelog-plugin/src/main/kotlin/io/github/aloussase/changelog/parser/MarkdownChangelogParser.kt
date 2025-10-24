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
        private val BRANCH_NAME_REGEX = Regex("## [\\w-]+")
        private val COMMIT_REGRX = Regex("- [\\w ]+")
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

            val branchName = lines[0]
            if (!BRANCH_NAME_REGEX.matches(branchName)) {
                return Result.failure(
                    GradleException("Expected a valid branch name, but got $branchName")
                )
            }

            val commits = arrayListOf<Commit>()

            for (line in lines.subList(1, lines.size)) {
                if (COMMIT_REGRX.matches(line)) {
                    commits.add(Commit(line.dropWhile { it == '-' || it == ' ' }))
                } else {
                    return Result.failure(
                        GradleException("Expected a valid commit message, but got $line")
                    )
                }
            }

            entries.add(
                ChangelogEntry(
                    branchName.dropWhile { it == '#' || it == ' ' },
                    commits
                )
            )
        }

        return Result.success(Changelog(entries))
    }
}
